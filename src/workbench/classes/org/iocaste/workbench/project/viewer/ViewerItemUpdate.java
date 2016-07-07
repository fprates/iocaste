package org.iocaste.workbench.project.viewer;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerUpdate;

public class ViewerItemUpdate implements ViewerUpdate {
    private String items;
    private Context extcontext;
    
    public ViewerItemUpdate(Context extcontext, String items) {
        this.items = items;
        this.extcontext = extcontext;
    }
    
    @Override
    public void add(Object object) {
        extcontext.add(items, (ExtendedObject)object);
    }

    @Override
    public void preexecute(ActionContext actionctx, ExtendedObject object) { }
    
    @Override
    public void remove(Object object) {
        extcontext.remove(items, (ExtendedObject)object);
    }
    
}