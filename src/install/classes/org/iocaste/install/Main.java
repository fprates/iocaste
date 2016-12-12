package org.iocaste.install;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.install.finish.FinishPage;
import org.iocaste.install.main.MainPage;
import org.iocaste.install.settings.SettingsPage;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        StandardPanel panel = new StandardPanel(context);
        Context extcontext = new Context(context);
        
        new Messages(context.messages);
        panel.instance("main", new MainPage(), extcontext);
        panel.instance("settings", new SettingsPage());
        panel.instance("finish", new FinishPage());
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}
