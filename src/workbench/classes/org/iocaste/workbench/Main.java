package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.texteditor.common.TextEditorTool;

public class Main extends AbstractPageBuilder {
    private static final String MAIN = "main";
    public static final String PRJC = "project_create";
    
    @Override
    public void config(PageBuilderContext context) {
        ViewContext view;
        Context extcontext = new Context();
        
        extcontext.repository = TextEditorTool.composeFileName(
                System.getProperty("user.dir"), "iocaste","iocaste-workbench");
        
        view = context.instance(MAIN);
        view.set(extcontext);
        view.set(new MainSpec());
        view.set(new MainConfig());
        view.put("create", new ProjectCreate());
        view.put("open", new ProjectOpen());
        
        
        view = context.instance(PRJC);
        view.set(extcontext);
        view.set(new ProjectSpec());
        view.set(new ProjectConfig());
        view.set(new ProjectInput());
        view.put("create", new ObjectProjectCreate());
        view.put("add", new ComponentViewAdd());
        view.put("components", new ComponentChoose());
        view.put("save", new ProjectSave());
        view.put("attributes", new AttributesSet());
        view.put("views", new ProjectViewDisplay());
        view.put("create_element", new SwitchPanel("element"));
        view.put("cancel_element", new SwitchPanel("model"));
        view.setUpdate(true);
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
