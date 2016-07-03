package org.iocaste.workbench.project.add;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.workbench.project.ParameterTransport;

public class ProjectAddPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new ProjectAddSpec());
        set(new ProjectAddConfig());
        set(new ProjectAddInput());
        action("project-add", new ParameterTransport(
                "project-add", "inputform"));
    }
}
