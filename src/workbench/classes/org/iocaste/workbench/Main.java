package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.workbench.install.ProjectInstall;
import org.iocaste.workbench.project.ProjectManager;

public class Main extends AbstractPageBuilder {
    
    @Override
    public void config(PageBuilderContext context) {
        StandardPanel panel = new StandardPanel(context);
        
        add(new Messages());
        context.addManager("project", new ProjectManager(this));
        panel.instance("main", new MainPage(), new Context(context));
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        
        defaultinstall.setProfile("DEVELOP");
        defaultinstall.setProgramAuthorization("WB");
        defaultinstall.setLink("WBDSPTCHR", "iocaste-workbench");
        defaultinstall.addToTaskGroup("DEVELOP", "WBDSPTCHR");
        
        installObject("project", new ProjectInstall());
    }

}
