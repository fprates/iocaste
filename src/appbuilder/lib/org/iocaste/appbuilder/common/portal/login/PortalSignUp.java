package org.iocaste.appbuilder.common.portal.login;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Shell;

public class PortalSignUp extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        /*
         * signup is triggered by a cancelable control. cancelable controls
         * don't save page position. we need to do it manually. they were
         * not designed to be used like this. even though, they are practical.
         */
        new Shell(context.function).pushPage(context.view);
        init("signup", getExtendedContext());
        redirect("signup");
    }
    
}