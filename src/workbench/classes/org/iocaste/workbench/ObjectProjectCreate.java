package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ObjectProjectCreate extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        int l;
        Context extcontext = getExtendedContext();
        String item = dbactionget("project", "create");
        
        switch(item) {
        case "view":
            l = extcontext.views.size();
            extcontext.views.put(
                    "view".concat(Integer.toString(l)), new ProjectView());
            break;
        }
        extcontext.viewtree = true;
        context.view.setReloadableView(true);
    }

}
