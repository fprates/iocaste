package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class GroupsPanelPage extends AbstractPanelPage {
    
    public final void execute() {
        set(new GroupsSpec());
        set(new GroupsConfig());
        set(new GroupsInput());
        put("group", new GroupsSelect());
        update();
    }
}
