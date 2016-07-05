package org.iocaste.workbench.project.viewer;

import org.iocaste.workbench.Context;

public interface ViewerItemLoader {
    
    public abstract void execute(
            ViewerItemPickData pickdata, Context excontext);
}