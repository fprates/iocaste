package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        Context extcontext;
        
        context.messages = new Messages();
        extcontext = new Context(context);
        context.add("main", new MainPage(), extcontext);
        context.add("display", new DisplayPage(), extcontext);
        context.add("update", new UpdatePage(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("SU01", "iocaste-usereditor");
        defaultinstall.addToTaskGroup("ADMIN", "SU01");
        defaultinstall.setProgramAuthorization("USEREDITOR");
        defaultinstall.setProfile("ADMIN");
        
        installObject("main", new Install());
    }
}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        
        action("display", new Dispatch("display"));
        action("update", new Dispatch("update"));
        action("create", new Create());
        action("delete", new Delete());
    }
    
}

class DisplayPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new DetailSpec());
        set(new DisplayConfig());
        set(new DetailInput());
    }
    
}

class UpdatePage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new DetailSpec());
        set(new UpdateConfig());
        set(new DetailInput());
        action("save", new Save());
    }
    
}