package org.iocaste.appbuilder.common;

public abstract class AbstractTableToolHandler implements TableToolHandler {
    protected ExtendedContext extcontext;
    
    @Override
    public final void setExtendedContext(ExtendedContext extcontext) {
        this.extcontext = extcontext;
    }
}
