package org.iocaste.workbench.project.viewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.workbench.project.ParameterTransport;

public class ProjectItemEditor extends AbstractPanelPage {
    private String view;
    private static final Map<String, String> commands;
    
    static {
        commands = new HashMap<>();
        commands.put("model_item", "model-item-add");
        commands.put("view_item", "viewspec-add");
    }
    
    public ProjectItemEditor(String view) {
        this.view = view;
    }
    
    @Override
    public void execute() throws Exception {
        set(new ProjectViewerSpec(view));
        set(new ProjectViewerConfig());
        set(new StandardViewInput());
        put(view.concat("_add"), new ParameterTransport(
                commands.get(view), view.concat("_detail")));
    }
    
}

