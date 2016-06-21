package org.iocaste.workbench.common.engine;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;

public class ApplicationEngine extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) {
        StandardPanel panel;
        
        panel = new StandardPanel(context);
        panel.instance("main", new MainPage());
    }
    
    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        installObject("auto", new AutomatedInstall(this, defaultinstall));
    }

}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
    }
    
}

class MainSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        // TODO Auto-generated method stub
        
    }
    
}

class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        // TODO Auto-generated method stub
        
    }
    
}
