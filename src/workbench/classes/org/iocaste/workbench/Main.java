package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.texteditor.common.TextEditorTool;

public class Main extends AbstractPageBuilder {
    private static final String MAIN = "main";
    public static final String PRJC = "project_create";
    
    @Override
    public void config(PageBuilderContext context) {
        Context extcontext = new Context();
        
        extcontext.repository = TextEditorTool.composeFileName(
                System.getProperty("user.dir"), "iocaste","iocaste-workbench");
        
        context.setUpdateViews(true);
        context.setExtendedContext(MAIN, extcontext);
        context.setViewSpec(MAIN, new MainSpec());
        context.setViewConfig(MAIN, new MainConfig());
        context.setActionHandler(MAIN, "create", new ProjectCreate());
        context.setActionHandler(MAIN, "open", new ProjectOpen());
        
        
        context.setExtendedContext(PRJC, extcontext);
        context.setViewSpec(PRJC, new ProjectSpec());
        context.setViewConfig(PRJC, new ProjectConfig());
        context.setViewInput(PRJC, new ProjectInput());
        context.setActionHandler(PRJC, "create", new ObjectProjectCreate());
        context.setActionHandler(PRJC, "add", new ComponentViewAdd());
        context.setActionHandler(PRJC, "components", new ComponentChoose());
        context.setActionHandler(PRJC, "save", new ProjectSave());
        context.setActionHandler(PRJC, "attributes", new AttributesSet());
        context.setActionHandler(PRJC, "views", new ProjectViewDisplay());
        context.setActionHandler(
                PRJC, "create_element", new SwitchPanel("element"));
        context.setActionHandler(
                PRJC, "cancel_element", new SwitchPanel("model"));
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        defaultinstall.setLink("WORKBENCH", "iocaste-workbench");
        defaultinstall.setProfile("WORKBENCH");
        defaultinstall.setProgramAuthorization("WORKBENCH.EXECUTE");
        defaultinstall.addToTaskGroup("DEVELOP", "WORKBENCH");
        
        installObject("models", new ModelsInstall());
    }

}
