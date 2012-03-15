package org.iocaste.sh;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

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
    public final ExtendedObject[] get(Message message) throws Exception {
        ExtendedObject[] itens;
        List<ExtendedObject> shdata;
        String value, name = message.getString("name");
        Documents documents = new Documents(this);
        ExtendedObject header = documents.getObject("SEARCH_HELP", name);
        
        if (header == null)
            return null;
        
        value = (String)header.getValue("EXPORT");
        header.setValue("EXPORT", value.split("\\.")[1]);
        
        itens = documents.select("from SH_ITENS where SEARCH_HELP = ?",
                header.getValue("NAME"));
        
        shdata = new ArrayList<ExtendedObject>();
        shdata.add(header);
        
        for (ExtendedObject item : itens) {
            value = (String)item.getValue("ITEM");
            item.setValue("ITEM", value.split("\\.")[1]);
            
            shdata.add(item);
        }
        
        return shdata.toArray(new ExtendedObject[0]);
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
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void update(Message message) throws Exception {
        ExtendedObject header = (ExtendedObject)message.get("header");
        ExtendedObject[] itens = (ExtendedObject[])message.get("itens"); 
    }

}
