package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.documents.common.ComplexModel;

public class MaintenanceSpec extends AbstractViewSpec {
    
    @Override
    public void execute() {
        Context extcontext = getExtendedContext();
        ComplexModel model = getManager(extcontext.cmodel).getModel();
        
        form("main");
        navcontrol("main");
        dataform("main", "head");
        tabbedpane("main", "tabs");
        tabbedpaneitem("tabs", "basetab");
        dataform("basetab", "base");
        for (String name : model.getItems().keySet()) {
            tabbedpaneitem("tabs", name);
            tabletool(name, name.concat("_table"));
        }
    }

}
