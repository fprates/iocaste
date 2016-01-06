package org.iocaste.workbench.project;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.Context;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceSpec;
import org.iocaste.documents.common.DocumentModel;

public class ProjectSpec extends MaintenanceSpec {
    
    @Override
    protected final void tabs(
            Context extcontext, Map<String, DocumentModel> models) {
        String addbar, tablename;
        String detail = null;
        
        for (String name : models.keySet()) {
            tablename = name.concat("_table");
            switch (name) {
            case "screen":
            case "model":
                tabbedpaneitem("tabs", name);
                addbar = name.concat("_addbar");
                standardcontainer(name, addbar);
                textfield(addbar, name.concat("_name"));
                button(addbar, name.concat("_add"));
                detail = name.concat("_detail");
                tabletool(name, tablename);
                
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
                tabletool(name, tablename);
                break;
            }
            
            extcontext.tabletools.put(tablename, new LinkedHashMap<>());
        }
    }
    
    private final void screendetail(String detail, String name) {
        tabbedpaneitem(detail.concat("_pane"), name);
        tabletool(name, name.concat("_table"));
    }
}
