package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ComponentChoose extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String name;
        String type = dbactionget("project", "components");
        Context extcontext = getExtendedContext();
        ProjectView project = extcontext.views.get(extcontext.view);
        
        project.count++;
        name = new StringBuilder(extcontext.name).append("_").append(type).
                append(project.count).toString();
        project.add(type, extcontext.name, name);
        extcontext.viewoptions = false;
        context.view.setReloadableView(true);
    }
}