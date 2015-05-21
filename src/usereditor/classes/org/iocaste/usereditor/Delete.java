package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Const;

public class Delete extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        
        extcontext = getExtendedContext();
        extcontext.userdata.username = getdfst("selection", "USERNAME");

        if (getObject("LOGIN", extcontext.userdata.username) == null) {
            message(Const.ERROR, "user.not.exists");
            return;
        }
        
        new Iocaste(context.function).dropUser(extcontext.userdata.username);
        message(Const.STATUS, "user.dropped");
    }

}
