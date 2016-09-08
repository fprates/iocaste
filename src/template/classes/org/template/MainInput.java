package org.template;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        textset("info", "server.test");
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
    
}