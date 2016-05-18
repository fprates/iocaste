package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class TasksSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        tiles(parent, "items");
    }
    
}
