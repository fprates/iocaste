package org.iocaste.appbuilder.common.cmodelviewer;

public class DisplayMaintenancePage extends AbstractEntityDisplayPage {

    public DisplayMaintenancePage() {
        super(new MaintenanceSpec(),
                new DisplayConfig(), new MaintenanceInput());
    }
     
}