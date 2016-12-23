package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.workbench.install.ClassesPackageInstall;
import org.iocaste.workbench.install.DataElementsInstall;
import org.iocaste.workbench.install.LinkInstall;
import org.iocaste.workbench.install.ModelsInstall;
import org.iocaste.workbench.install.ProjectHeadInstall;
import org.iocaste.workbench.install.ProjectInstall;
import org.iocaste.workbench.install.ViewInstall;
import org.iocaste.workbench.project.add.ProjectAddPage;
import org.iocaste.workbench.project.java.editor.ClassEditorPage;
import org.iocaste.workbench.project.viewer.ProjectItemEditor;
import org.iocaste.workbench.project.viewer.ProjectViewer;

public class Main extends AbstractPageBuilder {
    
    @Override
    public void config(PageBuilderContext context) {
        Context extcontext = new Context(context);
        
        context.messages = new Messages();
        context.add("main",
                new MainPage(), extcontext);
        context.add("project_add",
                new ProjectAddPage(), extcontext);
        context.add("project_viewer",
                new ProjectViewer(), extcontext);
        context.add("model_item_editor",
                new ProjectItemEditor("model_item", null), extcontext);
        context.add("view_item_editor",
                new ProjectItemEditor("view_item", "actions"), extcontext);
        context.add("package_item_editor",
                new ProjectItemEditor("package_item", null), extcontext);
        context.add("spec_config_editor",
                new ProjectItemEditor("spec_config", "tool_item"), extcontext);
        context.add("class-editor",
                new ClassEditorPage(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        defaultinstall.setProfile("DEVELOP");
        defaultinstall.setProgramAuthorization("WB");
        defaultinstall.setLink("WBDSPTCHR", "iocaste-workbench");
        defaultinstall.addToTaskGroup("DEVELOP", "WBDSPTCHR");
        
        installObject("projecthd", new ProjectHeadInstall());
        installObject("data_elements", new DataElementsInstall());
        installObject("models", new ModelsInstall());
        installObject("views", new ViewInstall());
        installObject("links", new LinkInstall());
        installObject("classes", new ClassesPackageInstall());
        installObject("project", new ProjectInstall());
    }

}
