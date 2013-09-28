package org.iocaste.dataeditor;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TableTool;

public class Context extends PageContext {
    public static final byte DISPLAY = 0;
    public static final byte UPDATE = 1;
    public ExtendedObject[] itens;
    public DocumentModel model, modelmodel;
    public Const viewtype;
    public byte mode;
    public TableTool tablehelper;
    public List<TableItem> deleted;
    
    public Context() {
        deleted = new ArrayList<>();
    }
}
