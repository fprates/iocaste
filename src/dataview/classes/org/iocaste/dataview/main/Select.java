package org.iocaste.dataview.main;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.dataview.Context;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;

public class Select extends AbstractActionHandler {
    
    public static final ExtendedObject[] load(String modelname,
            Documents documents, Object ns) {
        Query query = new Query();
        query.setModel(modelname);
        query.setNS(ns);
        return documents.select(query);
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        String modelname = getdfst("model", "NAME");

        extcontext.model = extcontext.documents.getModel(modelname);
        if (extcontext.model == null) {
            message(Const.ERROR, "invalid.model");
            return;
        }
        
        if (extcontext.model.getTableName() == null) {
            message(Const.ERROR, "is.reference.model");
            return;
        }
        
        extcontext.nsitem = extcontext.model.getNamespace();
        if (extcontext.nsitem != null) {
            init("nsinput", extcontext);
            redirect("nsinput");
            return;
        }
        
        extcontext.items = load(modelname, extcontext.documents, null);
        if (extcontext.items == null) {
            message(Const.ERROR, "no.results");
            return;
        }
        
        init("output", extcontext);
        redirect("output");
    }

}
