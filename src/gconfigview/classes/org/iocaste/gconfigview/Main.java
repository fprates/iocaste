package org.iocaste.gconfigview;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

/**
 * 
 * @author francisco.prates
 *
 */
public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        Context extcontext = new Context(context);
        context.messages = new Messages();
        context.add("main", new MainPage(), extcontext);
        context.add("edit", new DetailPage(Context.EDIT), extcontext);
        context.add("display", new DetailPage(Context.DISPLAY), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("GCONFIGVIEW", "iocaste-gconfigview");
        defaultinstall.addToTaskGroup("ADMIN", "GCONFIGVIEW");
        defaultinstall.setProfile("ADMIN");
        defaultinstall.setProgramAuthorization("GCONFIGVIEW");
    }
}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        action("display", new Load("display"));
        action("edit", new Load("edit"));
        update();
    }
    
}

class DetailPage extends AbstractPanelPage {
    private byte mode;
    
    public DetailPage(byte mode) {
        this.mode = mode;
    }
    
    @Override
    public void execute() {
        set(new DetailSpec());
        set(new DetailConfig(mode));
        set(new DetailInput());
        
        if (mode == Context.EDIT)
            action("save", new Save());
    }
    
}