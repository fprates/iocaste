package org.iocaste.workbench.project.viewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.workbench.project.compile.Compile;
import org.iocaste.workbench.project.java.PackageItemLoader;
import org.iocaste.workbench.project.view.ViewItemLoader;

public class ProjectViewer extends AbstractPanelPage {
    private static final Map<String, String[]> commands;
    private static final Map<String, ViewerItemPickData> pickdata;
    
    static {
        ViewerItemPickData data;
        
        commands = new HashMap<>();
        commands.put("models", new String[] {
                "model-add",
                "model-remove"
        });
        commands.put("views", new String[] {
                "view-add",
                "view-remove"
        });
        commands.put("data_elements", new String[] {
                "data-element-add",
                "data-element-remove"
        });
        commands.put("links", new String[] {
                "link-add",
                "link-remove"
        });
        commands.put("packages", new String[] {
                "package-add",
                "package-remove"
        });
        
        pickdata = new HashMap<>();
        data = new ViewerItemPickData("model_pick", pickdata);
        data.redirect = "model_item_editor";
        data.pickname = "models_items.NAME";
        data.command = "model-use";
        data.loader = new ViewerItemLoader("model_item_items", "item");
        
        data = new ViewerItemPickData("view_pick", pickdata);
        data.redirect = "view_item_editor";
        data.pickname = "views_items.NAME";
        data.command = "view-use";
        data.loader = new ViewItemLoader();
        
        data = new ViewerItemPickData("package_pick", pickdata);
        data.redirect = "package_item_editor";
        data.pickname = "packages_items.PACKAGE";
        data.command = null;
        data.loader = new PackageItemLoader();
    }
    
    private void action(String name) {
        put(name.concat("_add"), new AddParameterTransport(
                commands.get(name)[0], name.concat("_detail")));
        put(name.concat("_remove"), new RemoveParameterTransport(
                commands.get(name)[1], name.concat("_items")));
    }
    
    @Override
    public void execute() throws Exception {
        set(new ProjectViewerSpec());
        set(new ProjectViewerConfig());
        set(new StandardViewInput());
        set(new ProjectViewerStyle());
        action("compile", new Compile(getExtendedContext()));
        for (String name : commands.keySet())
            action(name);
        for (String action : pickdata.keySet())
            put(action, new ViewerItemPick(pickdata.get(action)));
    }
}

