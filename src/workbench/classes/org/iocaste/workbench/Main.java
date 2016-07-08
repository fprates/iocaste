package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.workbench.install.ClassesPackageInstall;
import org.iocaste.workbench.install.DataElementsInstall;
import org.iocaste.workbench.install.LinkInstall;
import org.iocaste.workbench.install.ModelsInstall;
import org.iocaste.workbench.install.ProjectHeadInstall;
import org.iocaste.workbench.install.ProjectInstall;
import org.iocaste.workbench.install.TextsInstall;
import org.iocaste.workbench.install.ViewInstall;
import org.iocaste.workbench.project.add.ProjectAddPage;
import org.iocaste.workbench.project.java.editor.ClassEditorPage;
import org.iocaste.workbench.project.viewer.ProjectItemEditor;
import org.iocaste.workbench.project.viewer.ProjectViewer;

public class Main extends AbstractPageBuilder {
    
    @Override
    public void config(PageBuilderContext context) {
        StandardPanel panel = new StandardPanel(context);
        Context extcontext = new Context(context);
        
        new Messages(context.messages);
        panel.instance("main",
                new MainPage(), extcontext);
        panel.instance("project_add",
                new ProjectAddPage(), extcontext);
        panel.instance("project_viewer",
                new ProjectViewer(), extcontext);
        panel.instance("model_item_editor",
                new ProjectItemEditor("model_item", null), extcontext);
        panel.instance("view_item_editor",
                new ProjectItemEditor("view_item", "actions"), extcontext);
        panel.instance("package_item_editor",
                new ProjectItemEditor("package_item", null), extcontext);
        panel.instance("spec_config_editor",
                new ProjectItemEditor("spec_config", "tool_item"), extcontext);
        panel.instance("class-editor",
                new ClassEditorPage(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        defaultinstall.setProfile("DEVELOP");
        defaultinstall.setProgramAuthorization("WB");
        defaultinstall.setLink("WBDSPTCHR", "iocaste-workbench");
        defaultinstall.addToTaskGroup("DEVELOP", "WBDSPTCHR");
        
        installObject("messages", new TextsInstall());
        installObject("projecthd", new ProjectHeadInstall());
        installObject("data_elements", new DataElementsInstall());
        installObject("models", new ModelsInstall());
        installObject("views", new ViewInstall());
        installObject("links", new LinkInstall());
        installObject("classes", new ClassesPackageInstall());
        installObject("project", new ProjectInstall());
    }

}
