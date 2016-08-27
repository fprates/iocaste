package org.iocaste.appbuilder.common.cmodelviewer;

public class MaintenancePage extends AbstractEntityCustomPage {
    
    public MaintenancePage() {
        super(new MaintenanceSpec(),
                new MaintenanceConfig(), new MaintenanceInput());
    }
}
