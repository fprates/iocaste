package org.iocaste.dataeditor.entry.select;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.dataeditor.Context;
import org.iocaste.shell.common.Const;

public class SelectEntry extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        Object value = getdfkey("selection");
        
        extcontext = getExtendedContext();
        extcontext.object = getObject(extcontext.model, extcontext.ns, value);
        if (extcontext.object == null) {
            message(Const.ERROR, "invalid.key");
            return;
        }
        
        context.function.dontPushPage();
        init("editentry", extcontext);
        redirect("editentry");
    }

}
