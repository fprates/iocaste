package org.iocaste.packagetool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
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
        export("is_installed", "isInstalled");
        export("uninstall", "uninstall");
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final Integer install(Message message) throws Exception {
        ExtendedObject header;
        ExtendedObject[] itens;
        DocumentModel tasks, shmodel, shimodel;
        SearchHelpData[] shdata;
        List<Object[]> values;
        String[] shitens;
        Map<String, String> links;
        SHLib shlib;
        int i;
        long pkgid;
        InstallData data = (InstallData)message.get("data");
        String shname, pkgname = message.getString("name");
        Documents documents = new Documents(this);
        Map<String, DocumentModelItem> shm =
                new HashMap<String, DocumentModelItem>();
        
        /*
         * Registra instalação do pacote
         */
        pkgid = documents.getNextNumber("PKGCODE") * 1000000;
        header = new ExtendedObject(documents.getModel("PACKAGE"));
        header.setValue("NAME", pkgname);
        header.setValue("CODE", pkgid);
        documents.save(header);
        
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
            
            pkgid++;
            Registry.add(model, pkgname, documents, pkgid);
            
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

            pkgid++;
            Registry.add(link, "TASK", pkgname, documents, pkgid);
        }
        
        /*
         * registra objetos de numeração
         */
        for (String factory : data.getNumberFactories()) {
            documents.createNumberFactory(factory);

            pkgid++;
            Registry.add(factory, "NUMBER", pkgname, documents, pkgid);
        }
        
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

                pkgid++;
                Registry.add(shname, "SH", pkgname, documents, pkgid);
            }
        }
        
        for (DataElement element : data.getElements()) {
            pkgid++;
            Registry.add(element.getName(), "DATA_ELEMENT", pkgname,
                    documents, pkgid);
        }
        
        documents.commit();
        
        return 1;
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final boolean isInstalled(Message message) throws Exception {
        String package_ = message.getString("package");
        ExtendedObject item = new Documents(this).
                getObject("PACKAGE", package_);
        
        return (item == null)? false : true;
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void uninstall(Message message) throws Exception {
        String model;
//        SHLib shlib;
        String pkgname = message.getString("package");
        ExtendedObject[] objects = Registry.getEntries(pkgname, this);
        List<ExtendedObject> shs = new ArrayList<ExtendedObject>();
        List<ExtendedObject> elements = new ArrayList<ExtendedObject>();
        
        for (ExtendedObject object : objects) {
            model = object.getValue("MODEL");
            
            if (model.equals("SEARCH_HELP")) {
                shs.add(object);
                continue;
            }
            
            if (model.equals("DATA_ELEMENT")) {
                elements.add(object);
                continue;
            }
        }
//        
//        shlib = new SHLib(this);
//        for (ExtendedObject object : shs) {
//            name = object.getValue("NAME");
//        }
    }
}
