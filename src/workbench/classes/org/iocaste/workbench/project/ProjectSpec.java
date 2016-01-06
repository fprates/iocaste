package org.iocaste.workbench.project;

import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceSpec;
import org.iocaste.documents.common.DocumentModel;

public class ProjectSpec extends MaintenanceSpec {
    
    @Override
    protected final void tabs(Map<String, DocumentModel> models) {
        for (String name : models.keySet()) {
            switch (name) {
            case "screen":
                tabbedpaneitem("tabs", name);
                tabletool(name, "screen_table");
                standardcontainer(name, "screen_detail");
                dataform("screen_detail", "screen_header");
                tabbedpane("screen_detail", "screen_detail_pane");
                break;
            case "screen_spec_item":
                screendetail(name);
                break;
            default:
                tabbedpaneitem("tabs", name);
                tabletool(name, name.concat("_table"));
                break;
            }
        }
    }
    
    private final void screendetail(String name) {
        tabbedpaneitem("screen_detail_pane", name);
        tabletool(name, name.concat("_table"));
    }
}
