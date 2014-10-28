package org.iocaste.kernel.documents;

public class LockEntry {
    private String sessionid;
    private String key;
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object object) {
        LockEntry lock;
        
        if (object == this)
            return true;
        
        if (!(object instanceof LockEntry))
            return false;
        
        lock = (LockEntry)object;
        if (!lock.getSessionid().equals(sessionid) ||
                !lock.getKey().equals(key))
            return false;
        
        return true;
    }
    
    public final String getKey() {
        return key;
    }
    
    public final String getSessionid() {
        return sessionid;
    }
    
    @Override
    public final int hashCode() {
        int hash = new StringBuilder(sessionid).append(key).
                append(".").toString().hashCode();
        return hash;
    }
    
    public final void setKey(String key) {
        this.key = key;
    }
    
    public final void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
