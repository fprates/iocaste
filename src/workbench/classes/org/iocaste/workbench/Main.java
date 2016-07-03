package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.workbench.install.ProjectInstall;
import org.iocaste.workbench.install.TextsInstall;
import org.iocaste.workbench.project.add.ProjectAddPage;
import org.iocaste.workbench.project.java.editor.ClassEditorPage;
import org.iocaste.workbench.project.viewer.ProjectViewer;

public class Main extends AbstractPageBuilder {
    
    @Override
    public void config(PageBuilderContext context) {
        StandardPanel panel = new StandardPanel(context);
        Context extcontext = new Context(context);
        
        new Messages(context.messages);
        panel.instance("main", new MainPage(), extcontext);
        panel.instance("project_add", new ProjectAddPage(), extcontext);
        panel.instance("project_viewer", new ProjectViewer(), extcontext);
        panel.instance("class-editor", new ClassEditorPage(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        
        defaultinstall.setProfile("DEVELOP");
        defaultinstall.setProgramAuthorization("WB");
        defaultinstall.setLink("WBDSPTCHR", "iocaste-workbench");
        defaultinstall.addToTaskGroup("DEVELOP", "WBDSPTCHR");
        
        installObject("messages", new TextsInstall());
        installObject("project", new ProjectInstall());
    }

}
