package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Const;

public class ShowObject extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        String name = getdfst("model", "NAME");
        Documents documents = new Documents(context.function);
        
        if (documents.getModel(name) == null) {
            message(Const.ERROR, "model.not.found");
            return;
        }
        
        extcontext.modelname = name;
        extcontext.model = documents.getModel(name);
        redirect(Main.STRUCTURE);
    }

}
