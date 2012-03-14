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
    
    private final String composeName(String model, Object item) {
        return new StringBuilder(model).append(".").
                append(item).toString();
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
        
        sh.setModelName((String)shdata.getValue("NAME"));
        sh.setExport((String)shdata.getValue("EXPORT"));
        
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
        String model, export, shname, shitemname;
        Documents documents = new Documents(this);
        ExtendedObject header = (ExtendedObject)message.get("header");
        ExtendedObject[] itens = (ExtendedObject[])message.get("itens");

        shname = (String)header.getValue("NAME");
        model = (String)header.getValue("MODEL");
        export = composeName(model, header.getValue("EXPORT"));
        
        header.setValue("EXPORT", export);
        documents.save(header);
        
        for (ExtendedObject item : itens) {
            shitemname = (String)item.getValue("ITEM");
            
            item.setValue("NAME", composeName(shname, shitemname));
            item.setValue("ITEM", composeName(model, shitemname));
            item.setValue("SEARCH_HELP", shname);
            
            documents.save(item);
        }
    }

}
