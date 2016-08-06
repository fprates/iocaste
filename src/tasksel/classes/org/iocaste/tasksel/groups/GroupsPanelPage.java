package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.tasksel.Style;

public class GroupsPanelPage extends AbstractPanelPage {
    
    public final void execute() {
        set(new GroupsSpec());
        set(new GroupsConfig());
        set(new GroupsInput());
        set(new Style());
        put("group", new GroupsSelect());
        update();
    }
}
