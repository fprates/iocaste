package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.StandardViewInput;

public class MaintenanceInput extends StandardViewInput {
    
    @Override
    protected void execute(PageBuilderContext context) {
        super.execute(context);
        loadInputTexts(context);
    }
}
