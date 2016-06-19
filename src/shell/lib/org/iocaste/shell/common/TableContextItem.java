package org.iocaste.shell.common;

import java.io.Serializable;

public class TableContextItem implements Serializable {
    private static final long serialVersionUID = -4990153739262387267L;
    public String text, htmlname;
    public boolean visible;
    
    public TableContextItem() {
        visible = true;
    }
}