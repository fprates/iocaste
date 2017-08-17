package org.iocaste.runtime.common.navcontrol;

import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.application.Context;

public class Logout extends AbstractActionHandler<Context> {

    @Override
    protected void execute(Context context) throws Exception {
        home();
        context.runtime().disconnect();
    }
    
}