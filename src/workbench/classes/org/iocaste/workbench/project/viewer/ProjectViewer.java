package org.iocaste.workbench.project.viewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.workbench.project.ParameterTransport;
import org.iocaste.workbench.project.datadict.ModelItemLoader;
import org.iocaste.workbench.project.view.ViewItemLoader;

public class ProjectViewer extends AbstractPanelPage {
    private static final Map<String, ViewerItemPickData> pickdata;
    
    static {
        ViewerItemPickData data;
        
        pickdata = new HashMap<>();
        data = new ViewerItemPickData("model_pick", pickdata);
        data.redirect = "model_item_editor";
        data.items = "models_items";
        data.command = "model-use";
        data.loader = new ModelItemLoader();
        data = new ViewerItemPickData("view_pick", pickdata);
        data.redirect = "view_item_editor";
        data.items = "views_items";
        data.command = "view-use";
        data.loader = new ViewItemLoader();
    }
    
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
        put("views_add", new ParameterTransport(
                "view-add", "views_detail"));
        for (String action : pickdata.keySet())
            put(action, new ViewerItemPick(pickdata.get(action)));
    }
}

