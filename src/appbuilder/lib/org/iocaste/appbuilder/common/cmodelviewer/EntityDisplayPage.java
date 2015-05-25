package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class EntityDisplayPage extends AbstractPanelPage {
    public AppBuilderLink link;
    
    @Override
    public void execute() {
        set(link.maintenancespec);
        set(link.displayconfig);
        set(link.maintenanceinput);
    }

}
