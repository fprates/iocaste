package org.iocaste.shell.common;

import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewInput;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.AbstractContext;

public interface CustomView {

    public abstract void execute(AbstractContext context, ViewSpecItem itemspec,
            String prefix);
    
    public abstract void execute(AbstractContext context) throws Exception;
    
    public abstract void setView(String view);
    
    public abstract void setViewConfig(ViewConfig viewconfig);
    
    public abstract void setViewInput(ViewInput viewinput);
    
    public abstract void setViewSpec(ViewSpec viewspec);
}
