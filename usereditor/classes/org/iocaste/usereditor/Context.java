package org.iocaste.usereditor;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.TableTool;

public class Context {
    public static final byte CREATE = 0;
    public static final byte DISPLAY = 1;
    public static final byte UPDATE = 2;
    public static final String[] TITLE = {
        "usereditor-create",
        "usereditor-display",
        "usereditor-update"
    };
    
    public byte mode;
    public UserData userdata;
    public Function function;
    public TableTool taskshelper, profileshelper;
    public DocumentModel usermodel, tasksmodel, profilesmodel;
}
