package org.iocaste.workbench.project;

import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceSpec;
import org.iocaste.documents.common.DocumentModel;

public class ProjectSpec extends MaintenanceSpec {
    
    @Override
    protected final void tabs(Map<String, DocumentModel> models) {
        String detail = null;
        
        for (String name : models.keySet()) {
            switch (name) {
            case "screen":
            case "model":
                tabbedpaneitem("tabs", name);
                detail = name.concat("_detail");
                tabletool(name, name.concat("_table"));
                standardcontainer(name, detail);
                dataform(detail, name.concat("_header"));
                tabbedpane(detail, detail.concat("_pane"));
                break;
            case "model_item":
            case "screen_spec_item":
                screendetail(detail, name);
                break;
            default:
                tabbedpaneitem("tabs", name);
                tabletool(name, name.concat("_table"));
                break;
            }
        }
    }
    
    private final void screendetail(String detail, String name) {
        tabbedpaneitem(detail.concat("_pane"), name);
        tabletool(name, name.concat("_table"));
    }
}
