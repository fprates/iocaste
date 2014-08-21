package org.iocaste.kernel.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable {
    private static final long serialVersionUID = 4538161172762638611L;
    private Map<String, Object> values;
    private String id;
    private String sessionid;
    private Exception ex;
    
    public Message(String id) {
        values = new HashMap<>();
        this.id = id;
    }

    /**
     * 
     * @param name
     * @param value
     */
    public final void add(String name, Object value) {
        values.put(name, value);
    }
    
    /**
     * 
     * @param name
     * @param value
     */
    public final void add(String name, int value) {
        values.put(name, value);
    }
    
    /**
     * 
     */
    public final void clear() {
        values.clear();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T> T get(String name) {
        return (T)values.get(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final boolean getbool(String name) {
        Object value = values.get(name);
        
        return (value == null)? false : (boolean)value;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final byte getb(String name) {
        Object value = values.get(name);
        
        return (value == null)? 0 : (byte)value;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final char getc(String name) {
        Object value = values.get(name);
        
        return (value == null)? 0 : (char)value;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final double getd(String name) {
        Object value = values.get(name);
        
        return (value == null)? 0 : (double)value;
    }
    
    /**
     * 
     * @return
     */
    public final Exception getException() {
        return ex;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final float getf(String name) {
        Object value = values.get(name);
        
        return (value == null)? 0 : (float)value;
    }
    
    /**
     * 
     * @return
     */
    public final String getId() {
        return id;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final int geti(String name) {
        Object value = values.get(name);
        
        return (value == null)? 0 : (int)value;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final long getl(String name) {
        Object value = values.get(name);
        
        return (value == null)? 0l : (long)value;
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, Object> getParameters() {
        return values;
    }
    
    /**
     * 
     * @return
     */
    public final String getSessionid() {
        return sessionid;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final short getsh(String name) {
        Object value = values.get(name);
        
        return (value == null)? 0 : (short)value;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final String getString(String name) {
        try {
            return (String)values.get(name);
        } catch (ClassCastException e) {
            return ((String[])values.get(name))[0];
        }
    }
    
    /**
     * 
     * @param ex
     */
    public final void setException(Exception ex) {
        this.ex = ex;
    }
    
    /**
     * 
     * @param sessionid
     */
    public final void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
