package org.iocaste.documents;

public class Lock {
    private String sessionid;
    private Object key;
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object object) {
        Lock lock;
        
        if (object == this)
            return true;
        
        if (!(object instanceof Lock))
            return false;
        
        lock = (Lock)object;
        if (!lock.getSessionid().equals(sessionid) ||
                !lock.getKey().equals(key))
            return false;
        
        return true;
    }
    
    public final Object getKey() {
        return key;
    }
    
    public final String getSessionid() {
        return sessionid;
    }
    
    public final void setKey(Object key) {
        this.key = key;
    }
    
    public final void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
