package org.iocaste.appbuilder.common.dataformtool;

import java.util.Map;

import org.iocaste.shell.common.Const;

public class DataFormToolItem {
    public String name;
    public Const componenttype;
    public boolean secret, focus, required;
    public Map<String, Object> values;
}
