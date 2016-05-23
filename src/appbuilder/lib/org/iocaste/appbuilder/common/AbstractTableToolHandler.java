package org.iocaste.appbuilder.common;

public abstract class AbstractTableToolHandler implements TableToolHandler {
    protected ExtendedContext extcontext;
    
    public AbstractTableToolHandler(ExtendedContext extcontext) {
        this.extcontext = extcontext;
    }
}
