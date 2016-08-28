package org.iocaste.external;

import org.iocaste.appbuilder.common.cmodelviewer.AbstractEntityCustomPage;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceInput;

public class ExternalCustomPage extends AbstractEntityCustomPage {
    
    public ExternalCustomPage() {
        super(new ExternalMaintenanceSpec(),
                new ExternalMaintenanceConfig(), new MaintenanceInput());
    }

    @Override
    public final void execute() {
        super.execute();
        set(new Style());
        put("importmodel", new ImportModel());
    }
}
