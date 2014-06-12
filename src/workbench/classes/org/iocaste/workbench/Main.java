package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;

public class Main extends AbstractPageBuilder {
    private static final String MAIN = "main";
    public static final String PRJC = "project_create";
    
    @Override
    public void config(PageBuilderContext context) {
        Context extcontext = new Context();
        
        extcontext.repository =
                "/home/francisco.prates/iocaste/iocaste-workbench";
        
        context.addExtendedContext(MAIN, extcontext);
        context.setViewSpec(MAIN, new MainSpec());
        context.setViewConfig(MAIN, new MainConfig());
        context.setActionHandler(MAIN, "create", new ProjectCreate());
        
        context.addExtendedContext(PRJC, extcontext);
        context.setViewSpec(PRJC, new ProjectSpec());
        context.setViewConfig(PRJC, new ProjectConfig());
        context.setViewInput(PRJC, new ProjectInput());
        context.setActionHandler(PRJC, "create", new ObjectProjectCreate());
        context.setActionHandler(PRJC, "add", new ComponentViewAdd());
        context.setActionHandler(PRJC, "components", new ComponentChoose());
        context.setActionHandler(PRJC, "save", new ProjectSave());
        context.setActionHandler(PRJC, "attributes", new AttributesSet());
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        defaultinstall.removeLink("IOCASTE-WORKBENCH");
        defaultinstall.setLink("WORKBENCH", "iocaste-workbench");
        defaultinstall.setProfile("WORKBENCH");
        defaultinstall.setProgramAuthorization("WORKBENCH.EXECUTE");
        defaultinstall.setTaskGroup("DEVELOP");
        
        installObject("models", new ModelsInstall());
    }

}
