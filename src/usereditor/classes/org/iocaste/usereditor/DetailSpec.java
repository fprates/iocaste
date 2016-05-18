package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class DetailSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        tabbedpane("content", "tabs");
        
        tabbedpaneitem("tabs", "idtab");
        dataform("idtab", "identity");
        
        tabbedpaneitem("tabs", "extrastab");
        dataform("extrastab", "extras");
        
        tabbedpaneitem("tabs", "taskstab");
        tabletool("taskstab", "tasks");
        
        tabbedpaneitem("tabs", "profiletab");
        tabletool("profiletab", "profiles");
    }

}
