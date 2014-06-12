package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ProjectViewDisplay extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        
        extcontext.view = dbactionget("project", "views");
        extcontext.hideAll();
        extcontext.viewtree = true;
    }

}
