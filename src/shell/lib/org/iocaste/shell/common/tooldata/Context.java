package org.iocaste.shell.common.tooldata;

import java.io.Serializable;

import org.iocaste.shell.common.View;

public interface Context extends Serializable {

    public abstract ToolData get(String name);
    
    public abstract View getView();
}
