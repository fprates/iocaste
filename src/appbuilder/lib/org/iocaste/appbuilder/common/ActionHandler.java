package org.iocaste.appbuilder.common;

import org.iocaste.shell.common.AbstractContext;

public interface ActionHandler {

    public abstract void run(AbstractContext context) throws Exception;
    
    public abstract void run(AbstractContext context, boolean redirectflag)
            throws Exception;
    
    public abstract void run(AbstractContext context, String page,
            boolean redirectflag) throws Exception;
}
