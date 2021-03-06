package org.iocaste.runtime.common.portal.login;

import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.application.Context;

public class PortalSignUp extends AbstractActionHandler<Context> {

    @Override
    protected void execute(Context context) throws Exception {
//        /*
//         * signup is triggered by a cancelable control. cancelable controls
//         * don't save page position. we need to do it manually. they were
//         * not designed to be used like this. even though, they are practical.
//         */
//        new Shell(context.function).pushPage(context.view);
        init("signup");
        redirect("signup");
    }
    
}