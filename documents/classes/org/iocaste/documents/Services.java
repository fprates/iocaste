package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.Map;


import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    private DocumentServices doc;
    
    public Services() {
        doc = new DocumentServices(this);
        
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
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void createModel(Message message) throws Exception {
        DocumentModel model = (DocumentModel)message.get("model");
        
        doc.createModel(model);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void delete(Message message) throws Exception {
        ExtendedObject object = (ExtendedObject)message.get("object");
        Iocaste iocaste = new Iocaste(this);
        
        doc.delete(iocaste, object);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception 
     */
    public final DataElement getDataElement(Message message) throws Exception {
        String name = message.getString("name");
        
        return doc.getDataElement(new Iocaste(this), name);
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
        
        return doc.getDocumentModel(documentname);
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
        DocumentModel model = doc.getDocumentModel(modelname);
        
        if (model == null)
            throw new Exception("invalid model.");
        
        return doc.getObject(model, key);
    }
    
    /**
     * Retorna próximo número do range
     * @param messagem
     * @return próximo número
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public final long getNextNumber(Message message) throws Exception {
        long current;
        Object[] lines;
        Map<String, Object> columns;
        String ident = message.getString("range");
        Iocaste iocaste = new Iocaste(this);
        
        if (ident == null)
            throw new Exception("Numeric range not specified.");

        lines = iocaste.select("select crrnt from range001 where ident = ?",
                new Object[] {ident});
        
        if (lines.length == 0)
            throw new Exception("Range \""+ident+"\" not found.");
        
        columns = (Map<String, Object>)lines[0];
        current = ((BigDecimal)columns.get("CRRNT")).longValue() + 1;
        iocaste.update("update range001 set crrnt = ? where ident = ?",
                new Object[] {current, ident});
        iocaste.commit();
        
        return current;
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final boolean hasModel(Message message) throws Exception {
        String modelname = message.getString("model_name");

        return doc.hasModel(modelname);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void modify(Message message) throws Exception {
        Iocaste iocaste = new Iocaste(this);
        ExtendedObject object = (ExtendedObject)message.get("object");
        
        doc.modify(iocaste, object);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void removeModel(Message message) throws Exception {
        String modelname = message.getString("model_name");
        DocumentModel model = doc.getDocumentModel(modelname);
        
        doc.removeModel(model);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void renameModel(Message message) throws Exception {
        String oldname = message.getString("oldname");
        String newname = message.getString("newname");
        
        doc.renameModel(oldname, newname);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int save(Message message) throws Exception {
        Iocaste iocaste = new Iocaste(this);
        ExtendedObject object = (ExtendedObject)message.get("object");
        
        return doc.save(iocaste, object);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final ExtendedObject[] select(Message message) throws Exception {
        String query = message.getString("query");
        Object[] criteria = (Object[])message.get("criteria");
        Iocaste iocaste = new Iocaste(this);
        
        return doc.select(iocaste, query, criteria);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void update(Message message) throws Exception {
        String query = message.getString("query");
        Object[] criteria = (Object[])message.get("criteria");
        
        doc.update(new Iocaste(this), query, criteria);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void updateModel(Message message) throws Exception {
        Iocaste iocaste = new Iocaste(this);
        DocumentModel model = (DocumentModel)message.get("model");
        
        doc.updateModel(iocaste, model);
    }
}
