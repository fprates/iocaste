package org.iocaste.dataeditor;

import java.util.HashSet;
import java.util.Set;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.AbstractContext;

public class Context extends AbstractContext {
    public static final byte DISPLAY = 0;
    public static final byte UPDATE = 1;
    public ExtendedObject[] itens;
    public DocumentModel modelmodel;
    public String model;
    public Const viewtype;
    public byte mode;
    public TableTool tablehelper;
    public Set<ExtendedObject> deleted;
    
    public Context() {
        deleted = new HashSet<>();
    }
}
