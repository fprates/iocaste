package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ObjectProjectCreate extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        String item = dbactionget("project", "create");
        
        switch(item) {
        case "view":
            now(extcontext);
            break;
        }
        
        extcontext.hideAll();
        extcontext.viewtree = true;
    }
    
    public static final ProjectView now(Context extcontext) {
        int l = extcontext.views.size();
        ProjectView view = new ProjectView();
        
        extcontext.views.put("view".concat(Integer.toString(l)), view);
        return view;
    }
}
