package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModelItem;

public class ConfigData {
    public static final byte DISPLAY = TableTool.DISPLAY;
    public static final byte UPDATE = TableTool.UPDATE;
    public DocumentModelItem hkey;
    public ComplexModel cmodel;
    public byte mode;
    public PageBuilderContext context;
}
