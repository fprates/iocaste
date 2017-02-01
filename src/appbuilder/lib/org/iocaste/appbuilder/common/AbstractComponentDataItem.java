package org.iocaste.appbuilder.common;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.shell.common.Const;

public class AbstractComponentDataItem {
    public boolean ns, invisible, required, disabled, focus;
    public String name, sh, label, action;
    public int length, vlength;
    public Map<String, Object> values;
    public Object value;
    public Const componenttype;
    public Set<String> validators;
    
    public AbstractComponentDataItem(String name) {
        this.name = name;
        validators = new LinkedHashSet<>();
    }
}