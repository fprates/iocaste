package org.iocaste.runtime.common.page;

import org.iocaste.runtime.common.application.Context;

public interface ViewInput {
    
    public abstract void run(Context context, boolean init);
}
