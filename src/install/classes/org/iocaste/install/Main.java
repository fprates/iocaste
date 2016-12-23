package org.iocaste.install;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.install.finish.FinishPage;
import org.iocaste.install.main.MainPage;
import org.iocaste.install.settings.SettingsPage;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        Context extcontext = new Context(context);
        context.messages = new Messages();
        context.add("main", new MainPage(), extcontext);
        context.add("settings", new SettingsPage());
        context.add("finish", new FinishPage());
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}
