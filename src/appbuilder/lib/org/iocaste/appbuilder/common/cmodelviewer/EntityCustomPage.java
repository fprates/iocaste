package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class EntityCustomPage extends AbstractPanelPage {
    public AppBuilderLink link;
    public AbstractActionHandler inputvalidate;
    
    @Override
    public void execute() {
        set(link.maintenancespec);
        set(link.maintenanceconfig);
        set(link.maintenanceinput);
        
        submit("validate", inputvalidate);
        action("save", link.save);
    }

}
