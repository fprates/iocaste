package org.iocaste.packagetool;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
//import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.AbstractFunction;
//import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("install", "install");
    }
    
    public final byte install(Message message) throws Exception {
//        ExtendedObject object;
        InstallData data = (InstallData)message.get("data");
        Documents documents = new Documents(this);
        
        for (DocumentModel model : data.getModels()) {
            if (documents.hasModel(model.getName()))
                documents.updateModel(model);
            else
                documents.createModel(model);
//            
//            object = new ExtendedObject(model);
//            
//            for (String key : data.get)
//            documents.save(object);
        }
        
        return 0;
    }
}
