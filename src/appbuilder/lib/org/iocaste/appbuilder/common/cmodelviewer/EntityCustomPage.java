package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class EntityCustomPage extends AbstractPanelPage {
    public AppBuilderLink link;
    
    @Override
    public void execute() {
        set(link.maintenancespec);
        set(link.maintenanceconfig);
        set(link.maintenanceinput);
        
        submit("validate", link.inputvalidate);
        action("save", link.save);
    }

}
