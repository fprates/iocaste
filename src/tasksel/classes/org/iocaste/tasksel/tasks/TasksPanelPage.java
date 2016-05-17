package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class TasksPanelPage extends AbstractPanelPage {
    
    public final void execute() {
        set(new TasksSpec());
        set(new TasksConfig());
        set(new TasksInput());
        put("task", new Call());
        update();
    }
}
