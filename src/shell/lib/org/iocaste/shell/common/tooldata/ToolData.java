package org.iocaste.shell.common.tooldata;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

public class ToolData implements Serializable {
	private static final long serialVersionUID = -4451606721765032982L;
    public Const componenttype;
	public ToolData nsitem, nsdata;
    public String sh, name, model, style, parent, group, label, actionname;
    public String text, tag, subpage, indexitem, pane, mask, image;
    public boolean internallabel, action, submit, disabled, secret, ns;
    public boolean invisible, required, focus, nolock, cancellable, absolute;
    public boolean mark, noheader;
    public int length, vlength, topline, step;
    public String[] groups, ordering;
	public Object[] textargs; 
    public Object nsvalue, value;
    public DocumentModel custommodel;
    public ViewSpecItem.TYPES type;
    public ExtendedObject object;
    public Map<Integer, ExtendedObject> objects;
    public Map<Integer, ObjectMetaData> metaobjects;
    public DataElement element;
    public Map<String, ToolData> items;
    public Map<String, Object> values;
    public Map<String, String> events, attributes, styles;
    public Set<String> validators, actions;
    
    public ToolData(TYPES type) {
        this(type, null);
    }
    
    public ToolData(TYPES type, String name) {
        this.type = type;
        this.name = name;
        items = new HashMap<>();
        attributes = new HashMap<>();
        actions = new LinkedHashSet<>();
        objects = new LinkedHashMap<>();
        metaobjects = new HashMap<>();
        events = new HashMap<>();
        validators = new HashSet<>();
        values = new LinkedHashMap<>();
        styles = new HashMap<>();
    }
    
    public final ToolData clone(String prefix, ToolData parent)
            throws Exception {
        Object valuefrom;
        ToolData data = new ToolData(type);
        Class<?> _class = getClass();
        
        for (Field field : _class.getFields()) {
            valuefrom = field.get(this);
            field.set(data, valuefrom);
        }
        
        data.name = String.format("%s_%s", prefix, data.name);
        data.parent = (this.parent == null)? parent.name :
            String.format("%s_%s", prefix, this.parent);
        return data;
    }
    
    public final Map<String, ToolData> get() {
        return items;
    }
    
    public final ToolData get(String name) {
        return items.get(name);
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
    
    public final int size() {
    	return items.size();
    }
    
    @Override
    public final String toString() {
    	return new StringBuilder(type.toString()).
    			append(": ").append(name).toString();
    }
}
