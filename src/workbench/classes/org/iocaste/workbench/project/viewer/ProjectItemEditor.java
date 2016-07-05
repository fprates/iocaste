package org.iocaste.workbench.project.viewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.workbench.project.ParameterTransport;
import org.iocaste.workbench.project.java.SourceConfigLoader;
import org.iocaste.workbench.project.view.SpecConfigLoader;

public class ProjectItemEditor extends AbstractPanelPage {
    private String view, extension;
    private static final Map<String, String> commands;
    private static final Map<String, ViewerItemPickData> pickdata;
    
    static {
        ViewerItemPickData data;
        
        commands = new HashMap<>();
        commands.put("model_item", "model-item-add");
        commands.put("view_item", "viewspec-add");
        commands.put("spec_config", "viewconfig");
        commands.put("actions", "action-add");
        commands.put("package_item", "class-add");

        pickdata = new HashMap<>();
        data = new ViewerItemPickData("spec_pick", pickdata);
        data.redirect = "spec_config_editor";
        data.pickname = "view_item_items.NAME";
        data.command = null;
        data.loader = new SpecConfigLoader();
        
        data = new ViewerItemPickData("source_pick", pickdata);
        data.redirect = "class-editor";
        data.pickname = "package_item_items.NAME";
        data.command = "class-edit";
        data.loader = new SourceConfigLoader();
    }
    
    public ProjectItemEditor(String view, String extension) {
        this.view = view;
        this.extension = extension;
    }
    
    private void action(String name) {
        put(name.concat("_add"), new ParameterTransport(
                commands.get(name), name.concat("_detail")));
    }
    
    @Override
    public void execute() throws Exception {
        set(new ProjectViewerSpec(view, extension));
        set(new ProjectViewerConfig());
        set(new StandardViewInput());
        set(new ProjectViewerStyle());
        action(view);
        if (extension != null)
            action(extension);
        for (String action : pickdata.keySet())
            put(action, new ViewerItemPick(pickdata.get(action)));
    }
    
}
