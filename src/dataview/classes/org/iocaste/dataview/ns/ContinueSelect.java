package org.iocaste.dataview.ns;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.dataview.Context;
import org.iocaste.dataview.main.Select;
import org.iocaste.shell.common.Const;

public class ContinueSelect extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        Object ns = getdfst("ns", "NSKEY");
        
        extcontext = getExtendedContext();
        extcontext.items = Select.load(
                extcontext.model.getName(), extcontext.documents, ns);
        if (extcontext.items == null) {
            message(Const.ERROR, "no.results");
            return;
        }
        
        init("output", extcontext);
        redirect("output");
    }

}
