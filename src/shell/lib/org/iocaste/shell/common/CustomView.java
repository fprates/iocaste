package org.iocaste.shell.common;

import org.iocaste.shell.common.AbstractContext;

public interface CustomView {
    
    public abstract void execute(AbstractContext context) throws Exception;
    
    public abstract void setView(String view);
}
