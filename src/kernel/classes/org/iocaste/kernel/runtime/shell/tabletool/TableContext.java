package org.iocaste.kernel.runtime.shell.tabletool;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.kernel.runtime.shell.ViewContext;

public class TableContext {
    public String name, htmlname;
    public ViewContext viewctx;
    public DocumentModel model;
    public TableTool tabletool;
    public int last;
    public Map<String, TableToolColumn> columns;
    
    public TableContext() {
        columns = new HashMap<>();
    }
}