package org.iocaste.workbench.project.viewer;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.ActionContext;

public interface ViewerUpdate {

    public abstract void add(Object object);
    
    public abstract void preexecute(
            ActionContext actionctx, ExtendedObject object);
    
    public abstract void remove(Object object);
}
