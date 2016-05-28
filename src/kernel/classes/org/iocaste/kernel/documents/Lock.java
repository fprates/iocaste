package org.iocaste.kernel.documents;

import java.util.HashSet;
import java.util.Set;

import org.iocaste.protocol.Message;

public class Lock extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        IsLocked islocked;
        Set<LockEntry> locks;
        LockEntry lock;
        String sessionid = message.getSessionid();
        String modelname = message.getst("model");
        String key = message.getst("key");
        Documents documents = getFunction();
        
        islocked = documents.get("is_locked");
        if (islocked.run(documents, modelname, key, sessionid))
            return 0;
        
        if (documents.lockcache.containsKey(modelname)) {
            locks = documents.lockcache.get(modelname);
        } else {
            locks = new HashSet<>();
            documents.lockcache.put(modelname, locks);
        }
        
        lock = new LockEntry();
        lock.setSessionid(sessionid);
        lock.setKey(key);
        locks.add(lock);
        
        return 1;
    }

}
