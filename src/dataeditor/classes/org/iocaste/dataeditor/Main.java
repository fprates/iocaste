package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.appbuilder.common.style.CommonStyle;
import org.iocaste.dataeditor.entry.AddEntry;
import org.iocaste.dataeditor.entry.EntryConfig;
import org.iocaste.dataeditor.entry.EntrySpec;
import org.iocaste.dataeditor.entry.EntryInput;
import org.iocaste.dataeditor.entry.select.SelectEntry;
import org.iocaste.dataeditor.entry.select.SelectEntryConfig;
import org.iocaste.dataeditor.entry.select.SelectEntrySpec;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;

public class Main extends AbstractPageBuilder {
    
    @Override
    public final void config(PageBuilderContext context) throws Exception {
        DocumentModel model;
        AbstractActionHandler load;
        StandardPanel panel;
        Context extcontext = new Context();
        
        extcontext.action = getParameter("action");
        extcontext.model = getParameter("model");
        extcontext.number = getParameter("number");
        extcontext.documents = new Documents(context.function);
        if (extcontext.action != null)
            extcontext.auto = true;
        if (extcontext.model != null) {
            model = new Documents(context.function).getModel(extcontext.model);
            if (model != null) {
                extcontext.appname = model.getPackage();
                setMessageSource(extcontext.appname);
            }
        }
        
        panel = new StandardPanel(context);
        panel.instance("nsinput", new NSPage(), extcontext);
        panel.instance("addentry", new EntryPage("add"), extcontext);
        panel.instance("editentry", new EntryPage("edit"), extcontext);
        panel.instance("select", new SelectPage(), extcontext);
        
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
        
        context.action = "load";
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
        
        action("new", new NewEntry());
        action("edit", new EditEntry());
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
        
        CommonStyle.get().head.bgcolor = "#3030ff";
        
        action("display", new Load("display"));
        action("edit", new Load("edit"));
    }
    
}

class NSPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new NSInputSpec());
        set(new NSInputConfig());
        action("continue", new ContinueSelect());
    }
    
}

class EntryPage extends AbstractPanelPage {
    private String action;
    
    public EntryPage(String action) {
        this.action = action;
    }
    
    @Override
    public void execute() {
        set(new EntrySpec());
        set(new EntryConfig());
        set(new EntryInput());
        action(action, new AddEntry());
    }
    
}

class SelectPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new SelectEntrySpec());
        set(new SelectEntryConfig());
        action("select", new SelectEntry());
    }
    
}