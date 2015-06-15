package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;

public class ContinueSelect extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;

        extcontext = getExtendedContext();
        extcontext.ns = getdfst("ns", "NSKEY");
        Load.execute(extcontext);
        if (extcontext.items == null) {
            message(Const.ERROR, "no.results");
            return;
        }
        
        init(extcontext.action, extcontext);
        redirect(extcontext.action);
    }

}
