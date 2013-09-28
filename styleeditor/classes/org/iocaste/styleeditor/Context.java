package org.iocaste.styleeditor;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.PageContext;

public class Context extends PageContext {
    public static final byte CREATE = 0;
    public static final byte SHOW = 1;
    public static final byte UPDATE = 2;
    public byte mode;
    public ExtendedObject[] elements;
}
