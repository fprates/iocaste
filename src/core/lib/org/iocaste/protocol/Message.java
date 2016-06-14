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
    public final void add(String name, byte value) {
        values.put(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     */
    public final void add(String name, char value) {
        values.put(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     */
    public final void add(String name, double value) {
        values.put(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     */
    public final void add(String name, float value) {
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
     * @param name
     * @param value
     */
    public final void add(String name, long value) {
        values.put(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     */
    public final void add(String name, short value) {
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
    public final boolean getbl(String name) {
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
        
        return (byte)((value == null)? 0 : (byte)value);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final char getc(String name) {
        Object value = values.get(name);
        
        return (char)((value == null)? 0 : value);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final double getd(String name) {
        Object value = values.get(name);
        
        return (double)((value == null)? 0 : value);
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
        
        return (float)((value == null)? 0 : value);
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
        
        return (int)((value == null)? 0 : value);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final long getl(String name) {
        Object value = values.get(name);
        
        return (long)((value == null)? 0 : value);
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
        
        return (short)((value == null)? 0 : value);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final String getst(String name) {
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
    
    @Override
    public final String toString() {
        Object value;
        StringBuilder sb = new StringBuilder();
        
        sb.append("function=").append(id).append("\n");
        for (String key : values.keySet()) {
            sb.append(key).append("=");
            value = values.get(key);
            if (value != null)
                sb.append(value.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
