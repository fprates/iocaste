package org.iocaste.workbench.project.viewer;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.workbench.project.ParameterTransport;

public class ProjectViewer extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new ProjectViewerSpec());
        set(new ProjectViewerConfig());
        set(new StandardViewInput());
        set(new ProjectViewerStyle());
        put("data_elements_add", new ParameterTransport(
                "data-element-add", "data_elements_detail"));
        put("models_add", new ParameterTransport(
                "model-add", "models_detail"));
    }
    
}

