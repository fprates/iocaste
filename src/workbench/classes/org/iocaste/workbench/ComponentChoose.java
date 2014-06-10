package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ComponentChoose extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String type = dbactionget("project", "components");
        Context extcontext = getExtendedContext();
        ProjectView project = extcontext.views.get(extcontext.view);
        
        project.add(type, extcontext.name, type);
        extcontext.viewoptions = false;
        context.view.setReloadableView(true);
    }
}
