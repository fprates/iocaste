package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;

public class Main extends AbstractPageBuilder {
    public static final String MAIN = "main";
    public static final String STRUCTURE = "tbstructure";
    
    @Override
    public void config(PageBuilderContext context) throws Exception {
        StandardPanel panel;
        Context extcontext = new Context(context);
        
        new Messages(context.messages);
        panel = new StandardPanel(context);
        panel.instance(MAIN, new MainPanel(), extcontext);
        panel.instance(STRUCTURE, new StructurePanel(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        defaultinstall.setLink("SE11", "iocaste-datadict");
        defaultinstall.addToTaskGroup("DEVELOP", "SE11");
        defaultinstall.setProfile("DEVELOP");
        defaultinstall.setProgramAuthorization("DDICT.EXECUTE");
        
        installObject("texts", new TextsInstall());
        installObject("models", new ModelsInstall());
    }
    
}

class MainPanel extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new SelectSpec());
        set(new SelectConfig());
        submit("show", new ShowObject());
    }
    
}

class StructurePanel extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new StructureSpec());
        set(new StructureConfig());
        set(new StandardViewInput());
    }
    
}