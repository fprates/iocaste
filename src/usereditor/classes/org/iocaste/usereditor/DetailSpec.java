package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class DetailSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        tabbedpane("content", "tabs");
        
        tabbedpaneitem("tabs", "idtab");
        dataform("idtab", "identity");
        
        tabbedpaneitem("tabs", "taskstab");
        tabletool("taskstab", "tasks");
        
        tabbedpaneitem("tabs", "profiletab");
        tabletool("profiletab", "profiles");
    }

}