package org.iocaste.kernel.documents;

import java.util.Set;

import org.iocaste.protocol.Message;

public class IsLocked extends AbstractDocumentsHandler {

    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        String modelname = message.getst("model");
        String key = message.getst("key");
        Documents documents = getFunction();
        
        return run(documents, modelname, key, sessionid);
    }
    
    public final boolean run(Documents documents, String modelname, String key,
            String sessionid) {
        Set<LockEntry> locks;
        Object testkey;
        
        if (!documents.lockcache.containsKey(modelname))
            return false;
        
        locks = documents.lockcache.get(modelname);
        for (LockEntry lock : locks) {
            testkey = lock.getKey();
            if (!testkey.equals(key))
                continue;
            
            if (lock.getSessionid().equals(sessionid))
                return false;
        }
        
        return true;
    }

}
