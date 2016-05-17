package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class TasksSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        tiles(parent, "items");
    }
    
}
