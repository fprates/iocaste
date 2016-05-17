package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class GroupsSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        tiles(parent, "items");
    }
    
}
