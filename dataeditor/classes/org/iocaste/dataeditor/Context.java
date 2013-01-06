package org.iocaste.dataeditor;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.TableTool;

public class Context {
    public static final byte DISPLAY = 0;
    public static final byte UPDATE = 1;
    
    public Function function;
    public ExtendedObject[] itens;
    public DocumentModel model, modelmodel;
    public Const viewtype;
    public byte mode;
    public TableTool tablehelper;
}
