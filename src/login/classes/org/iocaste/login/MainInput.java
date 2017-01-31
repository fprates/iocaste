package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainInput extends AbstractViewInput {
    
    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        dfset("login", extcontext.object);
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
        dflistset("login", "LOCALE", Context.LANGUAGES);
    }

}
