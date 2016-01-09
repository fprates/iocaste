package org.iocaste.workbench;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.cmodelviewer.InputValidate;

public class WorkbenchValidate extends InputValidate {

    @Override
    protected final void refresh(PageBuilderContext context) {
        String model;
        WorkbenchContext extcontext;
        
        super.refresh(context);
        model = getdfst("model_header", "MODEL_NAME");
        if (model == null)
            return;
        extcontext = getExtendedContext();
        extcontext.models.put(model, tableitemsget("model_item_table"));
    }
}
