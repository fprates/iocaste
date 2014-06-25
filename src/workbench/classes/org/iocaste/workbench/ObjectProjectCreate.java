package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ObjectProjectCreate extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ProjectView projectview;
        Context extcontext = getExtendedContext();
        String item = dbactionget("project", "create");

        extcontext.hideAll();
        switch(item) {
        case "view":
            projectview = now(extcontext);
            extcontext.view = projectview.name;
            extcontext.viewtree = true;
            break;
        case "model":
            extcontext.modelview = true;
            break;
        }
    }
    
    public static final ProjectView now(Context extcontext) {
        int l = extcontext.views.size();
        String name = "view".concat(Integer.toString(l));
        ProjectView view = new ProjectView(name);
        
        extcontext.views.put(name, view);
        return view;
    }
}
