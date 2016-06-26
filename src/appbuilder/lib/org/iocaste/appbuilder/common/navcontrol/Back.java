package org.iocaste.appbuilder.common.navcontrol;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class Back extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) {
        back();
    }
    
}