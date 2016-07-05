package org.iocaste.workbench.project.viewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.workbench.project.ParameterTransport;
import org.iocaste.workbench.project.view.SpecConfigLoader;

public class ProjectItemEditor extends AbstractPanelPage {
    private String view;
    private static final Map<String, String> commands;
    private static final Map<String, ViewerItemPickData> pickdata;
    
    static {
        ViewerItemPickData data;
        
        commands = new HashMap<>();
        commands.put("model_item", "model-item-add");
        commands.put("view_item", "viewspec-add");
        commands.put("spec_config", "viewconfig");

        pickdata = new HashMap<>();
        data = new ViewerItemPickData("spec_pick", pickdata);
        data.redirect = "spec_config_editor";
        data.items = "view_item_items";
        data.command = null;
        data.loader = new SpecConfigLoader();
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
        for (String action : pickdata.keySet())
            put(action, new ViewerItemPick(pickdata.get(action)));
    }
    
}

