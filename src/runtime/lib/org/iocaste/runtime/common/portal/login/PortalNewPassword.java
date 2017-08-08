package org.iocaste.runtime.common.portal.login;

import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.portal.PortalContext;

public class PortalNewPassword extends AbstractActionHandler<PortalContext> {

    @Override
    protected void execute(PortalContext context) throws Exception {
//        /*
//         * signup is triggered by a cancelable control. cancelable controls
//         * don't save page position. we need to do it manually. they were
//         * not designed to be used like this. even though, they are practical.
//         */
//        new Shell(context.function).pushPage(context.view);
        init("newpassword");
        redirect("newpassword");
    }
    
}