package org.iocaste.sh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    private Map<String, ExtendedObject[]> cache;
    
    public Services() {
        cache = new HashMap<>();
        
        export("get", "get");
        export("save", "save");
        export("assign", "assign");
        export("remove", "remove");
        export("unassign", "unassign");
        export("update", "update");
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final int assign(Message message) {
        DocumentModelItem item = message.get("model_item");
        Documents documents = new Documents(this);
        DocumentModel shref = documents.getModel("SH_REFERENCE");
        ExtendedObject reference = new ExtendedObject(shref);
        
        reference.setValue("MODEL_ITEM", Documents.getComposedName(item));
        reference.setValue("SEARCH_HELP", item.getSearchHelp());
        
        return documents.save(reference);
    }
    
    /**
     * 
     * @param model
     * @param item
     * @return
     */
    private final String composeName(String model, Object item) {
        return new StringBuilder(model).append(".").
                append(item).toString();
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final ExtendedObject[] get(Message message) throws Exception {
        String name = message.getString("name");
        
        return load(name);
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws Exception
     */
    private final ExtendedObject[] load(String name) throws Exception {
        Query query;
        Documents documents;
        ExtendedObject header;
        String value;
        ExtendedObject[] itens;
        List<ExtendedObject> shdata;
        
        if (cache.containsKey(name))
            return cache.get(name);
        
        documents = new Documents(this);
        header = documents.getObject("SEARCH_HELP", name);
        
        if (header == null)
            return null;
        
        value = header.getValue("EXPORT");
        header.setValue("EXPORT", value.split("\\.")[1]);
        
        query = new Query();
        query.setModel("SH_ITENS");
        query.andEqual("SEARCH_HELP", name);
        itens = documents.select(query);
        if (itens == null)
            throw new IocasteException("sh has no columns itens.");
        
        shdata = new ArrayList<>();
        shdata.add(header);
        
        for (ExtendedObject item : itens) {
            value = item.getValue("ITEM");
            item.setValue("ITEM", value.split("\\.")[1]);
            
            shdata.add(item);
        }
        
        itens = shdata.toArray(new ExtendedObject[0]);
        cache.put(name, itens);
        
        return itens;
    }

    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int remove(Message message) throws Exception {
        Query query;
        ExtendedObject[] shdata;
        Documents documents = new Documents(this);
        String shname = message.getString("shname");
        
        query = new Query();
        query.setModel("SH_REFERENCE");
        query.andEqual("SEARCH_HELP", shname);
        shdata = documents.select(query);
        if (shdata != null)
            throw new IocasteException(new StringBuilder(
                    "Search help has pendence on  ").
                    append(shdata[0].getValue("MODEL_ITEM")).toString());
        
        shdata = load(shname);
        for (int i = 1; i < shdata.length; i++)
            documents.delete(shdata[i]);
        
        cache.remove(shname);
        return documents.delete(shdata[0]);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void save(Message message) throws Exception {
        String model, export, shname, shitemname, exmessage;
        List<ExtendedObject> shdata;
        Documents documents;
        ExtendedObject header = message.get("header");
        ExtendedObject[] itens = message.get("itens");
        
        if (itens == null || itens.length == 0)
            if (itens == null)
                throw new IocasteException("sh has no columns itens.");
        
        documents = new Documents(this);
        model = header.getValue("MODEL");
        if (documents.getModel(model) == null)
            throw new IocasteException(model.concat(" is an invalid model."));

        shname = header.getValue("NAME");
        export = composeName(model, header.getValue("EXPORT"));
        header.setValue("EXPORT", export);
        if (documents.save(header) == 0)
            throw new Exception (new StringBuilder("Error saving header of " +
            		"sh ").append(shname).toString());

        shdata = new ArrayList<>();
        shdata.add(header);
        for (ExtendedObject item : itens) {
            shitemname = item.getValue("ITEM");
            
            item.setValue("NAME", composeName(shname, shitemname));
            item.setValue("ITEM", composeName(model, shitemname));
            item.setValue("SEARCH_HELP", shname);
            
            if (documents.save(item) == 0) {
                exmessage = new StringBuilder("Error saving line of sh ").
                        append(item.getValue("NAME")).toString();
                
                throw new IocasteException(exmessage);
            }
            
            shdata.add(item);
        }
        
        cache.put(shname, load(shname));
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final int unassign(Message message) {
        String shname = message.getString("name");
        Query query = new Query("delete");
        
        query.setModel("SH_REFERENCE");
        query.andEqual("SEARCH_HELP", shname);
        return new Documents(this).update(query);
    }
    
    /**
     * 
     * @param message
     */
    public final void update(Message message) {
        Query query;
        String model, export, shname, shitemname;
        ExtendedObject header = message.get("header");
        ExtendedObject[] itens = message.get("itens");
        Documents documents = new Documents(this);
        List<ExtendedObject> shdata = new ArrayList<>();
        
        shname = header.getValue("NAME");
        model = header.getValue("MODEL");
        export = composeName(model, header.getValue("EXPORT"));
        
        header.setValue("EXPORT", export);
        documents.modify(header);
        shdata.add(header);
        
        query = new Query("delete");
        query.setModel("SH_ITENS");
        query.andEqual("SEARCH_HELP", shname);
        documents.update(query);
        for (ExtendedObject item : itens) {
            shitemname = item.getValue("ITEM");
            
            item.setValue("NAME", composeName(shname, shitemname));
            item.setValue("ITEM", composeName(model, shitemname));
            item.setValue("SEARCH_HELP", shname);
            
            documents.save(item);
            shdata.add(item);
        }
        
        cache.remove(shname);
        cache.put(shname, shdata.toArray(new ExtendedObject[0]));
    }
}
