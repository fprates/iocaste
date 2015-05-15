package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        form("main");
        navcontrol("main");
        
        tabbedpane("main", "packages");
        
        tabbedpaneitem("packages", "installed");
        button("installed", "indetail");
        button("installed", "update");
        button("installed", "remove");
        tabletool("installed", "inpackages");
        
        tabbedpaneitem("packages", "uninstalled");
        button("uninstalled", "undetail");
        button("uninstalled", "install");
        tabletool("uninstalled", "unpackages");
        
        tabbedpaneitem("packages", "erinstalled");
        button("erinstalled", "erdetail");
        tabletool("erinstalled", "erpackages");
    }

}
