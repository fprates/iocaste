package org.iocaste.workbench.project.viewer;

import org.iocaste.workbench.Context;

public interface ViewerUpdate {

    public abstract void execute(Context extcontext, Object object);
}
