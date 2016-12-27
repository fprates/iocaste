package org.iocaste.sysconfig;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        context.messages = new Messages();
        context.add("main", new MainPanel(), new Context(context));
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("SYSCFG", "iocaste-sysconfig");
        defaultinstall.addToTaskGroup("ADMIN", "SYSCFG");
        defaultinstall.setProfile("ADMIN");
        defaultinstall.setProgramAuthorization("SYSCFG");
    }

}
