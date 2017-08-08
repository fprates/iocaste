package org.iocaste.runtime.common.page;

import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.application.Context;

public class Back extends AbstractActionHandler<Context> {

    @Override
    protected void execute(Context context) {
        context.popPage();
    }
    
}