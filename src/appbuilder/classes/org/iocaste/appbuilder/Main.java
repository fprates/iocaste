package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.docmanager.common.AbstractManager;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.protocol.Function;

public class Main extends AbstractPageBuilder {

    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.AbstractPageBuilder#config(
     *    org.iocaste.appbuilder.common.PageBuilderContext)
     */
    @Override
    public final void config(PageBuilderContext context) {
        Manager manager;
        MaintenanceConfig maintenanceconfig;
        AbstractViewSpec selspec, maintenancespec;
        AbstractViewInput maintenanceinput;
        AbstractActionHandler save;
        String create, create1, edit, edit1, display, display1, entityaction;
        ExtendedContext extcontext;
        String name = context.view.getParameter("name");
        String cmodel = context.view.getParameter("cmodel");

        manager = managerInstance(cmodel);
        
        create = name.concat("create");
        create1 = create.concat("1");
        edit = name.concat("edit");
        edit1 = edit.concat("1");
        display = name.concat("display");
        display1 = display.concat("1");
        context.addManager(create, manager);
        context.addManager(create1, manager);
        context.addManager(edit, manager);
        context.addManager(edit1, manager);
        context.addManager(display, manager);
        context.addManager(display1, manager);
        
        extcontext = new ExtendedContext();
        extcontext.redirect = create1;
        
        selspec = new SelectSpec();
        maintenancespec = new MaintenanceSpec();
        maintenanceconfig = new MaintenanceConfig();
        maintenanceinput = new MaintenanceInput(extcontext);
        save = new Save();
        
        context.setActionHandler(create, "create", new Validate(extcontext));
        context.setActionHandler(edit, "edit", new Load(extcontext, edit1));
        context.setActionHandler(display, "display",
                new Load(extcontext, display1));
        
        for (String action : new String[] {"create", "edit", "display"}) {
            entityaction = name.concat(action);
            context.setViewSpec(entityaction, selspec);
            context.setViewConfig(entityaction, new SelectConfig(action));
        }
        
        for (String view : new String[] {create1, edit1}) {
            context.setViewSpec(view, maintenancespec);
            context.setViewConfig(view, maintenanceconfig);
            context.setViewInput(view, maintenanceinput);
            context.setActionHandler(view, "save", save);
        }

        context.setViewSpec(display1, maintenancespec);
        context.setViewConfig(display1, new DisplayConfig());
        context.setViewInput(display1, maintenanceinput);
    }
    
    @Override
    protected final void installConfig(PageBuilderDefaultInstall defaultinstall)
    {
        defaultinstall.setProfile("APPBUILDER");
        defaultinstall.setTaskGroup(null);
        defaultinstall.setProgramAuthorization("APPBUILDER.EXECUTE");
    }
    
    private final Manager managerInstance(String cmodel) {
        return new RuntimeManager(cmodel, this);
    }
}

class RuntimeManager extends AbstractManager {

    public RuntimeManager(String cmodelname, Function function) {
        super(cmodelname, function);
        String messages[] = new String[3];
        
        messages[EEXISTS] = "code.exists";
        messages[EINVALID] = "invalid.code";
        messages[SAVED] = "record.saved";
        setMessages(messages);
    }
    
}