package org.iocaste.dataeditor;

import java.util.Map;

import org.iocaste.appbuilder.common.FieldProperty;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;

public class TableConfig {
    public byte mode;
    public boolean mark;
    public PageBuilderContext context;
    public Map<String, FieldProperty> properties;
    public DocumentModel model;
}
