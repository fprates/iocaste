package org.iocaste.packagetool;

import java.util.HashMap;
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
        ExtendedObject header, object;
        ExtendedObject[] itens;
        DocumentModel tasks, shmodel, shimodel;
        SearchHelpData[] shdata;
        Object[] values;
        String[] shitens;
        int i;
        Map<String, String> links;
        Map<String, DocumentModelItem> shm;
        SHLib shlib;
        InstallData data = (InstallData)message.get("data");
        Documents documents = new Documents(this);
        
        shm = new HashMap<String, DocumentModelItem>();
        
        for (DocumentModel model : data.getModels()) {
            if (documents.hasModel(model.getName())) {
                if (documents.updateModel(model) == 0)
                    throw new IocasteException("update model error.");
            } else {
                if (documents.createModel(model) == 0)
                    throw new IocasteException("create model error.");
            }
            
            values = data.getValues(model);
            object = (values == null)? null: new ExtendedObject(model);
            i = 0;
            
            for (DocumentModelItem modelitem : model.getItens()) {
                if (modelitem.getSearchHelp() != null)
                    shm.put(modelitem.getSearchHelp(), modelitem);
                
                if (values == null)
                    continue;
                
                object.setValue(modelitem, values[i++]);
            }
            
            if (values != null)
                documents.save(object);
        }
        
        tasks = documents.getModel("TASKS");
        links = data.getLinks();
        for (String link : links.keySet()) {
            object = new ExtendedObject(tasks);
            object.setValue("NAME", link);
            object.setValue("COMMAND", links.get(link));
            
            documents.save(object);
        }
        
        for (String factory : data.getNumberFactories())
            documents.createNumberFactory(factory);
        
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
        
        return 1;
    }
}
