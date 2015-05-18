package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.Colors;

public class EntityDisplayPage extends AbstractPanelPage {
    public AppBuilderLink link;
    
    @Override
    public void execute() {
        set(link.maintenancespec);
        set(link.displayconfig);
        set(link.maintenanceinput);
        set(Colors.CONTENT_BG, "#ffffff");
    }

}
