package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.tasksel.Style;

public class TasksPanelPage extends AbstractPanelPage {
    
    public final void execute() {
        set(new TasksSpec());
        set(new TasksConfig());
        set(new TasksInput());
        set(new Style());
        put("task", new Call());
        update();
    }
}
