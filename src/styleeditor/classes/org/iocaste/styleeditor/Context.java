package org.iocaste.styleeditor;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;

public class Context extends AbstractContext {
    public static final byte CREATE = 0;
    public static final byte SHOW = 1;
    public static final byte UPDATE = 2;
    public byte mode;
    public ExtendedObject[] elements, eproperties;
    public String element;
    public TableTool items, properties;
    public ExtendedObject header;
}
