package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
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
    public final void config(PageBuilderContext context) throws Exception {
        String module = context.view.getParameter("module");
        
        if (module == null)
            loadManagedModule(context);
        else
            loadRemoteModule(context, module);
    }
    
    @Override
    protected final void installConfig(PageBuilderDefaultInstall defaultinstall)
    {
        defaultinstall.setProfile("APPBUILDER");
        defaultinstall.setProgramAuthorization("APPBUILDER.EXECUTE");
    }
    
    private final Manager managerInstance(String cmodel) {
        return new RuntimeManager(cmodel, this);
    }
    
    private void loadManagedModule(PageBuilderContext context) {
        ViewContext viewctx;
        MaintenanceConfig maintenanceconfig;
        AbstractViewSpec selspec, maintenancespec;
        AbstractViewInput maintenanceinput;
        AbstractActionHandler save;
        String create, create1, edit, edit1, display, display1, entityaction;
        ExtendedContext extcontext;
        String name = context.view.getParameter("name");
        String cmodel = context.view.getParameter("cmodel");
        Manager manager = managerInstance(cmodel);
        
        create = name.concat("create");
        create1 = create.concat("1");
        edit = name.concat("edit");
        edit1 = edit.concat("1");
        display = name.concat("display");
        display1 = display.concat("1");
        context.addManager(cmodel, manager);
        
        extcontext = new ExtendedContext();
        extcontext.redirect = create1;
        
        selspec = new SelectSpec();
        maintenancespec = new MaintenanceSpec(cmodel);
        maintenanceconfig = new MaintenanceConfig(cmodel);
        maintenanceinput = new MaintenanceInput(extcontext);
        save = new Save(cmodel);
        
        for (String action : new String[] {"create", "edit", "display"}) {
            entityaction = name.concat(action);
            viewctx = context.instance(entityaction);
            viewctx.set(selspec);
            viewctx.set(new SelectConfig(action, cmodel));
            switch (action) {
            case "create":
                viewctx.put("create", new Validate(extcontext, cmodel));
                break;
            case "edit":
                viewctx.put("edit", new Load(extcontext, edit1, cmodel));
                break;
            case "display":
                viewctx.put("display", new Load(extcontext, display1, cmodel));
                break;
            }
        }
        
        for (String view : new String[] {create1, edit1}) {
            viewctx = context.instance(view);
            viewctx.set(maintenancespec);
            viewctx.set(maintenanceconfig);
            viewctx.set(maintenanceinput);
            viewctx.put("save", save);
        }

        viewctx = context.instance(display1);
        viewctx.set(maintenancespec);
        viewctx.set(new DisplayConfig(cmodel));
        viewctx.set(maintenanceinput);
    }
    
    private void loadRemoteModule(PageBuilderContext context, String module)
            throws Exception {
        String view;
        AutomatedViewSpec spec;
        int index;
        ViewSpecItem.TYPES[] types;
        String[] args;
        byte[] buffer = getApplicationContext(module);
        String[] lines = new String(buffer).split("\n");

        types = ViewSpecItem.TYPES.values();
        spec = null;
        for (String line : lines) {
            args = line.split(":");
            index = Integer.parseInt(args[1]);
            if ((index == -1) || (index >= 200))
                continue;
            
            switch (types[index]) {
            case VIEW:
                args = args[0].split("\\.");
                view = args[args.length - 1];
                spec = new AutomatedViewSpec();
                context.instance(view).set(spec);
                break;
            default:
                spec.add(args);
                break;
            }
        }
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