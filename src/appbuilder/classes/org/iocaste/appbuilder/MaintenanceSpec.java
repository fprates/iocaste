package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.documents.common.ComplexModel;

public class MaintenanceSpec extends AbstractViewSpec {
    
    @Override
    public void execute() {
        ComplexModel model = getManager().getModel();
        
        form("main");
        navcontrol("main");
        dataform("main", "head");
        tabbedpane("main", "tabs");
        tabbedpaneitem("tabs", "basetab");
        dataform("tabs", "base");
        for (String name : model.getItems().keySet()) {
            tabbedpaneitem("tabs", name);
            tabletool("tabs", name.concat("_table"));
        }
    }

}
