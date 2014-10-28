package org.iocaste.kernel.documents;

import java.util.Set;

import org.iocaste.protocol.Message;

public class Unlock extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        Set<LockEntry> locks;
        LockEntry lock;
        String sessionid = message.getSessionid();
        String modelname = message.getString("model");
        String key = message.getString("key");
        Documents documents = getFunction();
        IsLocked islocked = documents.get("is_locked");
        
        if (islocked.run(documents, modelname, key, sessionid))
            return 0;
        
        if (!documents.lockcache.containsKey(modelname))
            return 1;

        lock = new LockEntry();
        lock.setSessionid(sessionid);
        lock.setKey(key);
        
        locks = documents.lockcache.get(modelname);
        locks.remove(lock);
        if (locks.size() == 0)
            documents.lockcache.remove(modelname);
        
        return 1;
    }

}
