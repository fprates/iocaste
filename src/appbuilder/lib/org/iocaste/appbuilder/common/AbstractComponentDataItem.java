package org.iocaste.appbuilder.common;

import java.util.Map;

import org.iocaste.shell.common.Const;

public class AbstractComponentDataItem {
    public boolean ns, invisible, required, disabled, focus;
    public String name, sh, validator, label, action;
    public int length, vlength;
    public Map<String, Object> values;
    public Object value;
    public Const componenttype;
    
    public AbstractComponentDataItem(String name) {
        this.name = name;
    }
}