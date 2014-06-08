package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class ProjectSpec extends AbstractViewSpec {

    @Override
    protected void execute() {
        Context extcontext = getExtendedContext();
        
        form("main");
        navcontrol("main");
        
        dashboard("main", "project");
        dashboarditem("project", "create");
        dashboarditem("project", "viewoptions");
        
        standardcontainer("main", "view");
        nodelist("view", "viewtree");
        for (String view : extcontext.views.keySet())
            text("viewtree", view);
    }

}
