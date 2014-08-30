package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;

public class Main extends AbstractPageBuilder {
    public static final String MAIN = "main";
    public static final String STRUCTURE = "tbstructure";
    
    @Override
    public void config(PageBuilderContext context) throws Exception {
        ViewContext view;
        
        view = context.instance(MAIN);
        view.set(new SelectSpec());
        view.set(new SelectConfig());
//        context.setActionHandler(MAIN, "create", new CreateObject());
        view.put("show", new ShowObject());
//        context.setActionHandler(MAIN, "update", new UpdateObject());
//        context.setActionHandler(MAIN, "delete", new DeleteObject());
//        context.setActionHandler(MAIN, "rename", new RenameObject());
        
        view = context.instance(STRUCTURE);
        view.set(new StructureSpec());
        view.set(new StructureConfig());
        view.set(new StructureInput());
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