package org.iocaste.external;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;

public class ImportModel extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String modelname;
        DocumentModel model;
        ExternalContext extcontext;
        ExtendedObject object;
        
        modelname = getdfst("importobj", "NAME");
        if (modelname == null) {
            message(Const.ERROR, "mode.name.required");
            return;
        }
        
        model = new Documents(context.function).getModel(modelname);
        if (model == null) {
            message(Const.ERROR, "invalid.model");
            return;
        }
        
        extcontext = getExtendedContext();
        for (DocumentModelItem item : model.getItens()) {
            object = instance("XTRNL_STRUCTURE_ITEM");
            object.set("NAME", item.getName());
            object.set("TYPE", item.getDataElement().getType());
            extcontext.modelitems.add(object);
        }
    }

}
