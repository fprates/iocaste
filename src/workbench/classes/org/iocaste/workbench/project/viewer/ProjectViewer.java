package org.iocaste.workbench.project.viewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.workbench.project.ParameterTransport;
import org.iocaste.workbench.project.compile.Compile;
import org.iocaste.workbench.project.java.PackageItemLoader;

public class ProjectViewer extends AbstractPanelPage {
    private static final Map<String, ViewerItemPickData> pickdata;
    
    static {
        ViewerItemPickData data;
        
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
        data.loader = new ViewerItemLoader("view_item_items", "spec");
        
        data = new ViewerItemPickData("package_pick", pickdata);
        data.redirect = "package_item_editor";
        data.pickname = "packages_items.PACKAGE";
        data.command = null;
        data.loader = new PackageItemLoader();
    }
    
    @Override
    public void execute() throws Exception {
        set(new ProjectViewerSpec());
        set(new ProjectViewerConfig());
        set(new StandardViewInput());
        set(new ProjectViewerStyle());
        action("compile", new Compile(getExtendedContext()));
        put("data_elements_add", new ParameterTransport(
                "data-element-add", "data_elements_detail"));
        put("models_add", new ParameterTransport(
                "model-add", "models_detail"));
        put("views_add", new ParameterTransport(
                "view-add", "views_detail"));
        put("links_add", new ParameterTransport(
                "link-add", "links_detail"));
        put("packages_add", new ParameterTransport(
                "package-add", "packages_detail"));
        for (String action : pickdata.keySet())
            put(action, new ViewerItemPick(pickdata.get(action)));
    }
}

