package org.iocaste.sh;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.SearchHelp;

public class Services extends AbstractFunction {
    
    public Services() {
        export("get", "get");
        export("save", "save");
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final SearchHelp get(Message message) throws Exception {
        ExtendedObject[] objects;
        Container container = (Container)message.get("container");
        String name = message.getString("name");
        SearchHelp sh = new SearchHelp(container, name);
        Documents documents = new Documents(this);
        ExtendedObject shdata = documents.getObject("SEARCH_HELP", name);
        
        sh.setModelName((String)shdata.getValue("MODEL_NAME"));
        sh.setExport((String)shdata.getValue("EXPORT_ITEM"));
        
        objects = documents.select("from SH_ITENS where SH_NAME = ?",
                sh.getModelName());
        
        for (ExtendedObject object : objects)
            sh.addModelItemName((String)object.getValue("ITEM_NAME"));
        
        return sh;
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void save(Message message) throws Exception {
        String shname, shitemname;
        Documents documents = new Documents(this);
        ExtendedObject header = (ExtendedObject)message.get("header");
        ExtendedObject[] itens = (ExtendedObject[])message.get("itens");
        
        documents.save(header);
        shname = (String)header.getValue("NAME");
        
        for (ExtendedObject item : itens) {
            shitemname = new StringBuilder(shname).append(".").
                    append(item.getValue("NAME")).toString();
            
            item.setValue("NAME", shitemname);
            item.setValue("SEARCH_HELP", shname);
            
            documents.save(item);
        }
    }

}
