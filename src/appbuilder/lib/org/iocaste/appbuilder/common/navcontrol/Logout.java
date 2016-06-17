package org.iocaste.appbuilder.common.navcontrol;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.Iocaste;

public class Logout extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        redirect("iocaste-login", "authentic");
        new Iocaste(context.function).disconnect();
    }
    
}