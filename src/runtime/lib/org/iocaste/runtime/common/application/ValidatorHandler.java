package org.iocaste.runtime.common.application;

import org.iocaste.shell.common.tooldata.ToolData;

public interface ValidatorHandler {

    public abstract void run(Context context, ToolData tooldata)
            throws Exception;
}