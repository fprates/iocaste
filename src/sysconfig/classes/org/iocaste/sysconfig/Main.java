package org.iocaste.sysconfig;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.StandardPanel;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        StandardPanel panel;
        
        messages(new Messages());
        panel = new StandardPanel(context);
        panel.instance("main", new MainPanel(), new Context(context));
        context.getView("main").run("load", context);
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
