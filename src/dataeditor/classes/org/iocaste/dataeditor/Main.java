package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;

public class Main extends AbstractPageBuilder {
    
    @Override
    public final void config(PageBuilderContext context) throws Exception {
        AbstractActionHandler load;
        StandardPanel panel;
        Context extcontext = new Context();
        
        extcontext.action = getParameter("action");
        extcontext.model = getParameter("model");
        
        panel = new StandardPanel(context);
        
        if (extcontext.action == null) {
            load = new Load("main");
            panel.instance("main", new MainPage(), extcontext);
            panel.instance("edit", new EditPage(null), extcontext);
            panel.instance("display", new DisplayPage(null), extcontext);
            return;
        }
        
        load = new Load("main");
        switch (extcontext.action) {
        case "display":
            panel.instance("main", new DisplayPage(load), extcontext);
            break;
        case "edit":
            panel.instance("main", new EditPage(load), extcontext);
            break;
        default:
            return;
        }

        context.view.setActionControl("load");
        load.run(context, false);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("SM30", "iocaste-dataeditor");
        defaultinstall.setProgramAuthorization("DATAEDITOR");
        defaultinstall.addToTaskGroup("DEVELOP", "SM30");
        defaultinstall.setProfile("DEVELOP");
        
        installObject("messages", new TextsInstall());
    }
}

class DisplayPage extends AbstractPanelPage {
    private AbstractActionHandler load;
    
    public DisplayPage(AbstractActionHandler load) {
        this.load = load;
    }
    
    @Override
    public void execute() {
        set(new OutputSpec());
        set(new DisplayConfig());
        set(new ItemsInput());
        action("save", new Save());
        if (load != null)
            put("load", load);
        update();
    }
}

class EditPage extends AbstractPanelPage {
    private AbstractActionHandler load;
    
    public EditPage(AbstractActionHandler load) {
        this.load = load;
    }
    
    @Override
    public void execute() {
        set(new OutputSpec());
        set(new EditConfig());
        set(new ItemsInput());
        action("save", new Save());
        if (load != null)
            put("load", load);
        update();
    }
}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new SelectionSpec());
        set(new SelectionConfig());
        action("display", new Load("display"));
        action("edit", new Load("edit"));
    }
    
}