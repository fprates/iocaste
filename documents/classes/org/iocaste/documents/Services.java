package org.iocaste.documents;

import java.util.HashMap;
import java.util.Map;


import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    private Map<String, Map<String, String>> queries;
    
    public Services() {
        queries = new HashMap<String, Map<String, String>>();
        
        export("create_number_factory", "createNumberFactory");
        export("get_data_element", "getDataElement");
        export("get_next_number", "getNextNumber");
        export("get_object", "getObject");
        export("get_document_model", "getDocumentModel");
        export("has_model", "hasModel");
        export("create_model", "createModel");
        export("remove_model", "removeModel");
        export("rename_model", "renameModel");
        export("update_model", "updateModel");
        export("save", "save");
        export("select", "select");
        export("modify", "modify");
        export("delete", "delete");
        export("update", "update");
        export("validate_model", "validateModel");
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void createModel(Message message) throws Exception {
        DocumentModel model = (DocumentModel)message.get("model");
        
        Model.create(model, this, queries);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void createNumberFactory(Message message) throws Exception {
        String name = message.getString("name");
        
        NumberRange.create(name, this, queries);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void delete(Message message) throws Exception {
        ExtendedObject object = (ExtendedObject)message.get("object");
        Iocaste iocaste = new Iocaste(this);
        
        Query.delete(iocaste, object);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception 
     */
    public final DataElement getDataElement(Message message) throws Exception {
        String name = message.getString("name");
        
        return DataElementServices.get(new Iocaste(this), name);
    }
    
    /**
     * Retorna modelo de documento especificado.
     * @param mensagem
     * @return modelo de documento
     * @throws Exception
     */
    public final DocumentModel getDocumentModel(Message message)
            throws Exception {
        String documentname = message.getString("name");
        
        return Model.get(documentname, this, queries);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final ExtendedObject getObject(Message message) throws Exception {
        String modelname = message.getString("modelname");
        Object key = message.get("key");
        DocumentModel model = Model.get(modelname, this, queries);
        
        if (model == null)
            throw new Exception("invalid model.");
        
        return Query.get(model, key, this);
    }
    
    /**
     * Retorna próximo número do range
     * @param messagem
     * @return próximo número
     * @throws Exception
     */
    public final long getNextNumber(Message message) throws Exception {
        String ident = message.getString("range");
        
        return NumberRange.getCurrent(ident, this);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final boolean hasModel(Message message) throws Exception {
        String modelname = message.getString("model_name");

        return Model.has(modelname, this);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void modify(Message message) throws Exception {
        ExtendedObject object = (ExtendedObject)message.get("object");
        
        Query.modify(object, this);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void removeModel(Message message) throws Exception {
        String modelname = message.getString("model_name");
        DocumentModel model = Model.get(modelname, this, queries);
        
        Model.remove(model, this, queries);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void renameModel(Message message) throws Exception {
        String oldname = message.getString("oldname");
        String newname = message.getString("newname");
        
        Model.rename(oldname, newname, this, queries);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int save(Message message) throws Exception {
        ExtendedObject object = (ExtendedObject)message.get("object");
        
        return Query.save(object, this);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final ExtendedObject[] select(Message message) throws Exception {
        String query = message.getString("query");
        Object[] criteria = (Object[])message.get("criteria");
        
        return Query.select(query, criteria, this, queries);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void update(Message message) throws Exception {
        String query = message.getString("query");
        Object[] criteria = (Object[])message.get("criteria");
        
        Query.update(query, this, queries, criteria);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void updateModel(Message message) throws Exception {
        DocumentModel model = (DocumentModel)message.get("model");
        
        Model.update(model, this, queries);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final int validateModel(Message message) throws Exception {
        DocumentModel model = (DocumentModel)message.get("model");
        
        return Model.validate(model, this, queries);
    }
}
