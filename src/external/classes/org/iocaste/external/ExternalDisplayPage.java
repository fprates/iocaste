package org.iocaste.external;

import org.iocaste.appbuilder.common.cmodelviewer.AbstractEntityDisplayPage;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceInput;

public class ExternalDisplayPage extends AbstractEntityDisplayPage {

    public ExternalDisplayPage() {
        super(new ExternalMaintenanceSpec(),
                new ExternalDisplayConfig(), new MaintenanceInput());
    }
    
}