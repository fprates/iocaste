package org.iocaste.appbuilder.common.navcontrol;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Shell;

public class Logout extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String[] loginapp;
        
        loginapp = new Shell(context.function).getLoginApp().split("\\.");
        redirect(loginapp[0], loginapp[1]);
        new Iocaste(context.function).disconnect();
    }
    
}