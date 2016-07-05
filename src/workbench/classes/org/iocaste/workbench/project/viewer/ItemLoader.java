package org.iocaste.workbench.project.viewer;

import java.util.Map;

import org.iocaste.workbench.Context;

public interface ItemLoader {
    
    public abstract void execute(
            ViewerItemPickData pickdata, Context excontext);
    
    public abstract void init(Context excontext,
            ViewerItemPickData pickdata, Map<String, Object> parameters);
}