package org.iocaste.kernel.runtime.shell.tabletool;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.runtime.common.application.ToolData;

public class TableContext {
    public String htmlname;
    public ToolData data;
    public ViewContext viewctx;
    public DocumentModel model;
    public TableTool tabletool;
    public int last;
    public Map<String, TableToolColumn> columns;
    public Map<Integer, TableToolItem> items; 
    
    public TableContext() {
        columns = new HashMap<>();
        items = new HashMap<>();
    }
}