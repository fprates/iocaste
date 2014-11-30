package org.iocaste.shell.common;

import java.io.Serializable;

public class LinkEntry implements Serializable {
    private static final long serialVersionUID = -8709900758496846989L;
    public Object value;
    public int type;
    
    public LinkEntry(Object value, int type) {
        this.value = value;
        this.type = type;
    }
}
