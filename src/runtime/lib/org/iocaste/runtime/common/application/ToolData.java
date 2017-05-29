package org.iocaste.runtime.common.application;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.page.ViewSpecItem;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;
import org.iocaste.shell.common.Const;

public class ToolData implements Serializable {
	private static final long serialVersionUID = -4451606721765032982L;
    private Map<String, Object> properties;
    public Const componenttype;
	public ToolData nsitem, nsdata;
    public String sh, name, model, style, parent, group, label, actionname;
    public String text, tag;
    public boolean internallabel, action, submit, disabled, secret, ns;
    public boolean invisible, required, focus, container, control, datastore;
    public boolean nolock, cancellable, absolute;
    public int length, vlength, mode;
    public String[] groups, ordering;
	public Object[] textargs; 
    public Object value;
    public DocumentModel custommodel;
    public ViewSpecItem.TYPES type;
    public ExtendedObject object;
    public Map<Integer, ExtendedObject> objects;
    public DataElement element;
    public Map<String, ToolData> items;
    public Map<String, Object> values;
    public Map<String, String> attributes;
    public Set<String> validators, actions;
    
    public ToolData(TYPES type) {
        this.type = type;
        items = new HashMap<>();
        attributes = new HashMap<>();
        actions = new LinkedHashSet<>();
        properties = new HashMap<>();
        objects = new LinkedHashMap<>();
    }
    
    public final Map<String, ToolData> get() {
        return items;
    }
    
    public final ToolData get(String name) {
        return items.get(name);
    }
    
    public final boolean getbl(String property) {
        Object value = properties.get(property);
        return (boolean)((value == null)? false : value);
    }
    
    public final int geti(String property) {
        Object value = properties.get(property);
        return (int)((value == null)? 0 : value);
    }
    
    public final String getst(String property) {
        return (String)properties.get(property);
    }
    
    public final ToolData instance(String name) {
        ToolData item;
        
        item = get(name);
        if (item == null) {
            item = new ToolData(null);
            item.name = name;
            if (name != null)
                put(name, item);
        }
        return item;
    }
    
    public final ToolData nsItemInstance() {
        return (nsitem == null)? nsitem = instance(null) : nsitem;
    }
    
    private final void put(String name, ToolData item) {
        items.put(name, item);
    }
    
    public final void set(String property, Object value) {
        properties.put(property, value);
    }
    
    public final int size() {
    	return items.size();
    }
    
    @Override
    public final String toString() {
    	return new StringBuilder(type.toString()).
    			append(": ").append(name).toString();
    }
}
