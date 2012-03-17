package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("install", "install");
    }
    
    public final byte install(Message message) throws Exception {
        ExtendedObject object;
        DocumentModel tasks;
        Object[] values;
        int i;
        Map<String, String> links;
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
        
        return 0;
    }
}
