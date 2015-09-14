package org.iocaste.copy;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        StandardPanel panel;
        Context extcontext;
        
        extcontext = new Context();
        panel = new StandardPanel(context);
        panel.instance("main", new MainPage(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("COPY", "iocaste-copy");
        defaultinstall.addToTaskGroup("ADMIN", "COPY");
        defaultinstall.setProgramAuthorization("COPY");
        defaultinstall.setProfile("ADMIN");
    }

}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new InputSpec());
        set(new InputConfig());
        submit("copy", new Copy());
    }
    
}