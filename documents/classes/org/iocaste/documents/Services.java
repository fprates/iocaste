package org.iocaste.documents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    private Cache cache;
    private Map<String, Set<Lock>> lockcache;
    
    public Services() {
        cache = new Cache(this);
        lockcache = new HashMap<String, Set<Lock>>();
        
        export("create_model", "createModel");
        export("create_number_factory", "createNumberFactory");
        export("delete", "delete");
        export("get_data_element", "getDataElement");
        export("get_next_number", "getNextNumber");
        export("get_object", "getObject");
        export("get_document_model", "getDocumentModel");
        export("is_locked", "isLocked");
        export("lock", "lock");
        export("modify", "modify");
        export("remove_model", "removeModel");
        export("remove_number_factory", "removeNumberFactory");
        export("rename_model", "renameModel");
        export("save", "save");
        export("select", "select");
        export("unlock", "unlock");
        export("update", "update");
        export("update_model", "updateModel");
        export("validate_model", "validateModel");
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int createModel(Message message) throws Exception {
        DocumentModel model = message.get("model");
        
        return Model.create(model, cache);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void createNumberFactory(Message message) throws Exception {
        String name = message.getString("name");
        
        NumberRange.create(name, cache);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final int delete(Message message) throws Exception {
        ExtendedObject object = message.get("object");
        Iocaste iocaste = new Iocaste(this);
        
        return Query.delete(iocaste, object);
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
        
        return Model.get(documentname, cache);
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
        DocumentModel model = Model.get(modelname, cache);
        
        if (model == null)
            throw new IocasteException("invalid model.");
        
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
    
    public final boolean isLocked(Message message) {
        String sessionid = message.getSessionid();
        String modelname = message.getString("model");
        Object key = message.get("key");
        
        return isLocked(modelname, key, sessionid);
    }
    
    private final boolean isLocked(String modelname, Object key,
            String sessionid) {
        Set<Lock> locks;
        Object testkey;
        
        if (!lockcache.containsKey(modelname))
            return false;
        
        locks = lockcache.get(modelname);
        for (Lock lock : locks) {
            testkey = lock.getKey();
            if (!testkey.equals(key))
                continue;
            
            if (lock.getSessionid().equals(sessionid))
                return false;
        }
        
        return true;
    }
    
    public final int lock(Message message) throws Exception {
        Set<Lock> locks;
        Lock lock;
        String sessionid = message.getSessionid();
        String modelname = message.getString("model");
        Object key = message.get("key");
        
        if (isLocked(modelname, key, sessionid))
            return 0;
        
        if (lockcache.containsKey(modelname)) {
            locks = lockcache.get(modelname);
        } else {
            locks = new HashSet<Lock>();
            lockcache.put(modelname, locks);
        }
        
        lock = new Lock();
        lock.setSessionid(sessionid);
        lock.setKey(key);
        locks.add(lock);
        
        return 1;
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int modify(Message message) throws Exception {
        ExtendedObject object = message.get("object");
        
        return Query.modify(object, this);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int removeModel(Message message) throws Exception {
        String modelname = message.getString("model_name");
        DocumentModel model = Model.get(modelname, cache);
        
        return Model.remove(model, cache);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int removeNumberFactory(Message message) throws Exception {
        String name = message.getString("name");
        
        return NumberRange.remove(name, cache);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int renameModel(Message message) throws Exception {
        String oldname = message.getString("oldname");
        String newname = message.getString("newname");
        
        return Model.rename(oldname, newname, cache);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int save(Message message) throws Exception {
        ExtendedObject object = message.get("object");
        
        return Query.save(object, this);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final ExtendedObject[] select(Message message) throws Exception {
        String query = message.getString("query");
        Object[] criteria = message.get("criteria");
        int rows = message.getInt("rows");
        
        return Query.select(query, rows, cache, criteria);
    }
    
    public final int unlock(Message message) {
        Set<Lock> locks;
        Lock lock;
        String sessionid = message.getSessionid();
        String modelname = message.getString("model");
        Object key = message.get("key");
        
        if (isLocked(modelname, key, sessionid))
            return 0;
        
        if (!lockcache.containsKey(modelname))
            return 1;

        lock = new Lock();
        lock.setSessionid(sessionid);
        lock.setKey(key);
        
        locks = lockcache.get(modelname);
        locks.remove(lock);
        
        return 1;
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int update(Message message) throws Exception {
        String query = message.getString("query");
        Object[] criteria = message.get("criteria");
        
        return Query.update(query, cache, criteria);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int updateModel(Message message) throws Exception {
        DocumentModel model = message.get("model");
        
        return Model.update(model, cache);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final int validateModel(Message message) throws Exception {
        DocumentModel model = message.get("model");
        
        return Model.validate(model, cache);
    }
}
