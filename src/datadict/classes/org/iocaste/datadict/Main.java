package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;

public class Main extends AbstractPageBuilder {
    public static final String MAIN = "main";
    public static final String STRUCTURE = "tbstructure";
    
    @Override
    public void config(PageBuilderContext context) throws Exception {
        context.setViewSpec(MAIN, new SelectSpec());
        context.setViewConfig(MAIN, new SelectConfig());
//        context.setActionHandler(MAIN, "create", new CreateObject());
        context.setActionHandler(MAIN, "show", new ShowObject());
//        context.setActionHandler(MAIN, "update", new UpdateObject());
//        context.setActionHandler(MAIN, "delete", new DeleteObject());
//        context.setActionHandler(MAIN, "rename", new RenameObject());
        
        context.setViewSpec(STRUCTURE, new StructureSpec());
        context.setViewConfig(STRUCTURE, new StructureConfig());
        context.setViewInput(STRUCTURE, new StructureInput());
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        defaultinstall.setLink("SE11", "iocaste-datadict");
        defaultinstall.addToTaskGroup("DEVELOP", "SE11");
        defaultinstall.setProfile("DEVELOP");
        defaultinstall.setProgramAuthorization("DDICT.EXECUTE");
        
        installObject("models", new ModelsInstall());
    }
    
}