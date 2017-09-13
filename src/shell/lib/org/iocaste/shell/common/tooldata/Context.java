package org.iocaste.shell.common.tooldata;

import org.iocaste.shell.common.View;

public interface Context {

    public abstract ToolData get(String name);
    
    public abstract View getView();
}
