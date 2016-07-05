package org.iocaste.workbench.project.viewer;

import org.iocaste.workbench.Context;

public interface ItemLoader {
    
    public abstract void execute(
            ViewerItemPickData pickdata, Context excontext);
}