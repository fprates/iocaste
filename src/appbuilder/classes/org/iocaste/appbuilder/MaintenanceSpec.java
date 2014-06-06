package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.documents.common.ComplexModel;

public class MaintenanceSpec extends AbstractViewSpec {
    
    @Override
    public void execute() {
        ComplexModel model = getManager().getModel();
        
        form("main");
        navControl("main");
        dataForm("main", "head");
        tabbedPane("main", "tabs");
        tabbedPaneItem("tabs", "basetab");
        dataForm("tabs", "base");
        for (String name : model.getItems().keySet()) {
            tabbedPaneItem("tabs", name);
            tableTool("tabs", name.concat("_table"));
        }
    }

}