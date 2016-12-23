package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.dataeditor.display.DisplayNoLoadPage;
import org.iocaste.dataeditor.display.DisplayPage;
import org.iocaste.dataeditor.edit.EditNoLoadPage;
import org.iocaste.dataeditor.edit.EditPage;
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
        Context extcontext = new Context(context);

        context.messages = new Messages();
        extcontext.action = getParameter("action");
        extcontext.model = getParameter("model");
        extcontext.number = getParameter("number");
        extcontext.documents = new Documents(context.function);
        if (extcontext.action != null)
            extcontext.auto = true;
        if (extcontext.model != null) {
            model = new Documents(context.function).getModel(extcontext.model);
            if (model != null)
                extcontext.appname = model.getPackage();
        }
        
        context.add("nsinput", new NSPage(), extcontext);
        context.add("addentry", new EntryPage("add"), extcontext);
        context.add("editentry", new EntryPage("edit"), extcontext);
        context.add("select", new SelectPage(), extcontext);
        
        if (extcontext.action == null) {
            context.add("main", new MainPage(), extcontext);
            context.add("edit", new EditNoLoadPage(), extcontext);
            context.add("display", new DisplayNoLoadPage(), extcontext);
            return;
        }
        
        switch (extcontext.action) {
        case "display":
            context.add("main", new DisplayPage(), extcontext);
            break;
        case "edit":
            context.add("main", new EditPage(), extcontext);
            break;
        default:
            return;
        }
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("SM30", "iocaste-dataeditor");
        defaultinstall.setProgramAuthorization("DATAEDITOR");
        defaultinstall.addToTaskGroup("DEVELOP", "SM30");
        defaultinstall.setProfile("DEVELOP");
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