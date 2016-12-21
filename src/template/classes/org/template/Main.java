package org.template;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;

/**
 * Template par módulo interno do iocaste.
 * @author francisco.prates
 *
 */
public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        StandardPanel panel = new StandardPanel(context);
        messages(new Messages());
        panel.instance("main", new MainPage());
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("BIND", "iocaste-template");
        defaultinstall.addToTaskGroup("TEMPLATE", "BIND");
        defaultinstall.setProfile("TEMPLATE");
        defaultinstall.setProgramAuthorization("TEMPLATE.EXECUTE");
        installObject("messages", new Install());
    }
}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
        put("bind", new Bind());
    }
    
}
