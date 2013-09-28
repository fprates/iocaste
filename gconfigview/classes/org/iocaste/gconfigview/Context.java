package org.iocaste.gconfigview;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.PageContext;

public class Context extends PageContext {
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
