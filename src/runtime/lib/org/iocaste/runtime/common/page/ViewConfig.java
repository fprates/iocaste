package org.iocaste.runtime.common.page;

import org.iocaste.runtime.common.application.Context;

public interface ViewConfig {
    
    public abstract void run(Context context);
    
    public abstract void run(Context context, String prefix);
    
//    public abstract void setNavControl(NavControl navcontrol);
}
