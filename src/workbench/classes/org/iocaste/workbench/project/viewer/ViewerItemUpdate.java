package org.iocaste.workbench.project.viewer;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;

public class ViewerItemUpdate implements ViewerUpdate {
    private String items;
    
    public ViewerItemUpdate(String items) {
        this.items = items;
    }
    
    @Override
    public void execute(Context extcontext, Object object) {
        extcontext.add(items, (ExtendedObject)object);
    }
    
}