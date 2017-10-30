package org.iocaste.shell.common.tooldata;

import java.io.Serializable;

import org.iocaste.documents.common.ExtendedObject;

public class MetaObject implements Serializable {
    private static final long serialVersionUID = -1516668987365387901L;
    public ExtendedObject object;
    public boolean selected;
    
    public MetaObject(ExtendedObject object) {
        this(object, false);
    }
    
    public MetaObject(ExtendedObject object, boolean selected) {
        this.object = object;
        this.selected = selected;
    }
}