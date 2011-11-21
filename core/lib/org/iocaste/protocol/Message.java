package org.iocaste.protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable {
    private static final long serialVersionUID = 4538161172762638611L;
    private Map<String, Object> values;
    private String id;
    private String sessionid;
    private Exception ex;
    
    public Message() {
        values = new HashMap<String, Object>();
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
    public final Object get(String name) {
        return values.get(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final boolean getBoolean(String name) {
        return (Boolean)values.get(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final byte getByte(String name) {
        return (Byte)values.get(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final char getChar(String name) {
        return (Character)values.get(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final double getDouble(String name) {
        return (Double)values.get(name);
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
    public final float getFloat(String name) {
        return (Float)values.get(name);
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
    public final int getInt(String name) {
        return (Integer)values.get(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final long getLong(String name) {
        return (Long)values.get(name);
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
    public final short getShort(String name) {
        return (Short)values.get(name);
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
     * @param id
     */
    public final void setId(String id) {
        this.id = id;
    }
    
    /**
     * 
     * @param sessionid
     */
    public final void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
