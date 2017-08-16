package org.iocaste.runtime.common.managedview.edit;

import java.util.Map;

import org.iocaste.appbuilder.common.FieldProperty;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.runtime.common.application.Context;

public class ConfigData {
    public static final byte DISPLAY = TableTool.DISPLAY;
    public static final byte UPDATE = TableTool.UPDATE;
    public DocumentModelItem hkey;
    public ComplexModel cmodel;
    public byte mode;
    public boolean mark;
    public Context context;
    public Map<String, FieldProperty> fieldproperties;
}
