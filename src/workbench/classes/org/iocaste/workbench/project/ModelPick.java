package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.WorkbenchContext;
import org.iocaste.workbench.WorkbenchValidate;

public class ModelPick extends WorkbenchValidate {

    @Override
    protected void execute(PageBuilderContext context) {
        String model;
        ExtendedObject modelheader;
        WorkbenchContext extcontext;
        
        super.execute(context);
        model = getinputst("model_table.MODEL_NAME");
        modelheader = getdf("model_header");
        modelheader.set("MODEL_NAME", model);

        extcontext = getExtendedContext();
        extcontext.dataforms.put("model_header", modelheader);
        extcontext.set("model_item_table", extcontext.models.get(model));
    }

}
