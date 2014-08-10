package org.iocaste.appbuilder.common.tabletool;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Container;

public class TableToolData implements Serializable {
    private static final long serialVersionUID = -5741139329515555543L;
    public Container container;
    public String name, borderstyle, itemcolumn, model;
    public boolean mark, enabled;
    public int vlines, step, last, increment;
    public byte mode;
    public ExtendedObject[] objects;
    public String[] hide, show, enableonly;
    public Map<String, TableToolColumn> columns;
    
    public TableToolData() {
        vlines = 15;
        step = 1;
        enabled = true;
        increment = 1;
        columns = new HashMap<>();
    }
}
