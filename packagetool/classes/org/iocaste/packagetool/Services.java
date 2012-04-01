package org.iocaste.packagetool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.SHLib;

public class Services extends AbstractFunction {

    public Services() {
        export("install", "install");
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final Integer install(Message message) throws Exception {
        String shname;
        ExtendedObject header;
        ExtendedObject[] itens;
        DocumentModel tasks, shmodel, shimodel;
        SearchHelpData[] shdata;
        List<Object[]> values;
        String[] shitens;
        Map<String, String> links;
        SHLib shlib;
        int i;
        InstallData data = (InstallData)message.get("data");
        Documents documents = new Documents(this);
        Map<String, DocumentModelItem> shm =
                new HashMap<String, DocumentModelItem>();
        
        /*
         * gera modelos;
         * insere registros;
         * prepara dados para ajuda de pesquisa.
         */
        for (DocumentModel model : data.getModels()) {
            if (documents.getModel(model.getName()) != null) {
                if (documents.updateModel(model) == 0)
                    throw new IocasteException("update model error.");
            } else {
                if (documents.createModel(model) == 0)
                    throw new IocasteException("create model error.");
            }
            
            for (DocumentModelItem modelitem : model.getItens())
                if (modelitem.getSearchHelp() != null)
                    shm.put(modelitem.getSearchHelp(), modelitem);
            
            /*
             * recupera modelo para trazer as queries.
             */
            model = documents.getModel(model.getName());
            values = data.getValues(model);
            
            if (values == null)
                continue;
            
            for (Object[] line : values) {
                header = new ExtendedObject(model);
                i = 0;
                
                for (DocumentModelItem modelitem : model.getItens())
                    header.setValue(modelitem, line[i++]);
                
                documents.save(header);
            }
        }
        
        /*
         * registra tarefas
         */
        tasks = documents.getModel("TASKS");
        links = data.getLinks();
        for (String link : links.keySet()) {
            header = new ExtendedObject(tasks);
            header.setValue("NAME", link);
            header.setValue("COMMAND", links.get(link));
            
            documents.save(header);
        }
        
        /*
         * registra objetos de numeração
         */
        for (String factory : data.getNumberFactories())
            documents.createNumberFactory(factory);
        
        /*
         * gera ajudas de pesquisa
         */
        shdata = data.getSHData();
        if (shdata.length > 0) {
            shlib = new SHLib(this);
            shmodel = documents.getModel("SEARCH_HELP");
            shimodel = documents.getModel("SH_ITENS");
            
            for (SearchHelpData shd : shdata) {
                shname = shd.getName();
                header = new ExtendedObject(shmodel);
                header.setValue("NAME", shname);
                header.setValue("MODEL", shd.getModel());
                header.setValue("EXPORT", shd.getExport());
                
                shitens = shd.getItens();
                itens = new ExtendedObject[shitens.length];
                i = 0;
                
                for (String name : shitens) {
                    itens[i] = new ExtendedObject(shimodel);
                    itens[i].setValue("NAME", name);
                    itens[i].setValue("SEARCH_HELP", shname);
                    itens[i++].setValue("ITEM", name);
                }
                
                shlib.save(header, itens);
                
                if (shm.containsKey(shname))
                    shlib.assign(shm.get(shname));
            }
        }
        
        documents.commit();
        
        return 1;
    }
}
