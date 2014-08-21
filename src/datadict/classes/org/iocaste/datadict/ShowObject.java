package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;

public class ShowObject extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Query query;
        Context extcontext = new Context();
        String name = getdfst("model", "NAME");
        Documents documents = new Documents(context.function);
        
        if (documents.getModel(name) == null) {
            message(Const.ERROR, "model.not.found");
            return;
        }
        
        extcontext.modelname = name;
        extcontext.head = documents.getObject("MODEL", name);
        
        query = new Query();
        query.setModel("MODELITEM");
        query.andEqual("MODEL", name);
        extcontext.items = documents.select(query);
        context.setExtendedContext(Main.STRUCTURE, extcontext);
        redirect(Main.STRUCTURE);
    }

}
