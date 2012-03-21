package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.SHLib;

public class Services extends AbstractFunction {

    public Services() {
        export("install", "install");
    }
    
    public final byte install(Message message) throws Exception {
        ExtendedObject header, object;
        ExtendedObject[] itens;
        DocumentModel tasks, shmodel, shimodel;
        SearchHelpData[] shdata;
        Object[] values;
        String[] shitens;
        int i;
        Map<String, String> links;
        SHLib shlib;
        InstallData data = (InstallData)message.get("data");
        Documents documents = new Documents(this);
        
        for (DocumentModel model : data.getModels()) {
            if (documents.hasModel(model.getName()))
                documents.updateModel(model);
            else
                documents.createModel(model);
            
            values = data.getValues(model);
            if (values == null)
                continue;
            
            i = 0;
            object = new ExtendedObject(model);
            for (DocumentModelItem modelitem : model.getItens())
                object.setValue(modelitem, values[i++]);
            
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
                header = new ExtendedObject(shmodel);
                header.setValue("NAME", shd.getName());
                header.setValue("MODEL", shd.getModel());
                header.setValue("EXPORT", shd.getExport());
                
                shitens = shd.getItens();
                itens = new ExtendedObject[shitens.length];
                i = 0;
                
                for (String name : shitens) {
                    itens[i] = new ExtendedObject(shimodel);
                    itens[i].setValue("NAME", name);
                    itens[i].setValue("SEARCH_HELP", shd.getName());
                    itens[i++].setValue("ITEM", name);
                }
                
                shlib.save(header, itens);
            }
        }
        
        return 0;
    }
}
