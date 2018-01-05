package org.iocaste.runtime.common.application;

import org.iocaste.shell.common.tooldata.ToolData;

public abstract class AbstractValidatorHandler<T extends Context>
        extends AbstractActionHandler<T> implements ValidatorHandler {
    
    @Override
    protected final void execute(T context) { }
    
    protected abstract void execute(T context, ToolData tooldata);

    @Override
    public final boolean isValidator() {
        return true;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public final void run(Context context, ToolData tooldata) throws Exception
    {
        run(context);
        execute((T)context, tooldata);
    }
}