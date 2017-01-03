package org.iocaste.copy;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.copy.install.DBInstall;
import org.iocaste.copy.main.MainPage;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        context.messages = new Messages();
        context.add("main", new MainPage(), new Context(context));
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("COPY", "iocaste-copy");
        defaultinstall.addToTaskGroup("ADMIN", "COPY");
        defaultinstall.setProgramAuthorization("COPY");
        defaultinstall.setProfile("ADMIN");
        installObject("db", new DBInstall());
    }

}
