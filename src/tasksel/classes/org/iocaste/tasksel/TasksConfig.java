package org.iocaste.tasksel;

import java.util.Set;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;

public class TasksConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        String text;
        Set<TaskEntry> entries;
        DashboardComponent dash;
        Context extcontext = getExtendedContext();
        DashboardFactory groups = getDashboard("groups");

        groups.isometricGrid();
        groups.setArea(100, 100, "%");
        for (String name : extcontext.groups.keySet()) {
            entries = extcontext.groups.get(name);
            dash = getDashboardItem("groups", name);
            dash.addText(name);
            for (TaskEntry entry : entries) {
                text = entry.getText();
                if (text == null)
                    text = entry.getName();
                dash.add(text, entry.getName());
            }
        }
    }
}
