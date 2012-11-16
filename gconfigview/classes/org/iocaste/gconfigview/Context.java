package org.iocaste.gconfigview;

import org.iocaste.documents.common.ExtendedObject;

public class Context {
    public static final byte DISPLAY = 0;
    public static final byte EDIT = 1;
    public static final byte SELECT = 2;
    public static final String[] TITLES = {
        "config.display",
        "config.edit",
        "config.select"
    };
    
    public byte mode;
    public ExtendedObject[] objects;
}
