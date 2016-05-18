package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class GroupsSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        tiles(parent, "items");
    }
    
}
