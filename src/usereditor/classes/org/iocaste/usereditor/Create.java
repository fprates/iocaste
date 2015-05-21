package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;

public class Create extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        
        extcontext = getExtendedContext();
        extcontext.userdata.identity = null;
        extcontext.userdata.profiles = null;
        extcontext.userdata.tasks = null;
        extcontext.userdata.username = getdfst("selection", "USERNAME");
        if (getObject("LOGIN", extcontext.userdata.username) != null) {
            message(Const.ERROR, "user.already.exists");
            return;
        }
        
        init("update", extcontext);
        redirect("update");
    }

}
