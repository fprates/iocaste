package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;

public class Main extends AbstractPageBuilder {

    private final ViewContext buildDisplayView(
            PageBuilderContext context, ExtendedContext extcontext, String name)
    {
        ViewContext view;
        AbstractViewSpec outputspec;
        AbstractViewInput itemsinput;

        outputspec = new OutputSpec();
        itemsinput = new ItemsInput();
        
        view = context.instance(name);
        view.set(outputspec);
        view.set(new DisplayConfig());
        view.set(itemsinput);
        view.set(extcontext);
        view.put("save", new Save());
        view.setUpdate(true);
        
        return view;
    }
    
    private final ViewContext buildEditView(
            PageBuilderContext context, ExtendedContext extcontext, String name)
    {
        ViewContext view;
        AbstractViewSpec outputspec;
        AbstractViewInput itemsinput;

        outputspec = new OutputSpec();
        itemsinput = new ItemsInput();
        
        view = context.instance(name);
        view.set(outputspec);
        view.set(new EditConfig());
        view.set(itemsinput);
        view.set(extcontext);
        view.setUpdate(true);
        
        return view;
    }
    
    @Override
    public final void config(PageBuilderContext context) throws Exception {
        AbstractActionHandler load;
        ViewContext view;
        Context extcontext = new Context();
        
        extcontext.action = getParameter("action");
        extcontext.model = getParameter("model");
        
        if (extcontext.action != null) {
            switch (extcontext.action) {
            case "display":
                view = buildDisplayView(context, extcontext, "main");
                break;
            case "edit":
                view = buildEditView(context, extcontext, "main");
                break;
            default:
                return;
            }

            context.view.setActionControl("load");
            load = new Load(extcontext.action);
            view.put("load", load);
            load.run(context, false);
            return;
        }
        
        view = context.instance("main");
        view.set(new SelectionSpec());
        view.set(new SelectionConfig());
        view.put("display", new Load("display"));
        view.put("edit", new Load("edit"));
        view.set(extcontext);
        
        view = buildEditView(context, extcontext, "edit");
        view.put("save", new Save());
        
        buildDisplayView(context, extcontext, "display");
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
