package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.docmanager.common.AbstractManager;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.protocol.Function;

public abstract class AbstractModelViewer extends AbstractPageBuilder {
    public static final String CREATE = "create";
    public static final String DISPLAY = "display";
    public static final String EDIT = "edit";
    
    @Override
    protected abstract void installConfig(
            PageBuilderDefaultInstall defaultinstall);
    
    protected final void loadManagedModule(PageBuilderContext context,
            String name, String cmodel, String msgsource) {
        ViewContext viewctx;
        MaintenanceConfig maintenanceconfig;
        AbstractViewSpec selspec, maintenancespec;
        AbstractViewInput maintenanceinput;
        AbstractActionHandler save, inputvalidate;
        String create, create1, edit, edit1, display, display1, entityaction;
        Context extcontext;
        Manager manager;
        
        if (msgsource != null)
            setMessageSource(msgsource);
        
        create = name.concat(CREATE);
        create1 = create.concat("1");
        edit = name.concat(EDIT);
        edit1 = edit.concat("1");
        display = name.concat(DISPLAY);
        display1 = display.concat("1");
        
        extcontext = new Context();
        extcontext.number = getParameter("number");
        extcontext.redirect = (extcontext.number == null)? create1 : edit1;
        extcontext.cmodel = cmodel;
        
        manager = managerInstance(extcontext.cmodel);
        context.addManager(extcontext.cmodel, manager);
        
        selspec = new SelectSpec();
        maintenancespec = new MaintenanceSpec();
        maintenanceconfig = new MaintenanceConfig();
        maintenanceinput = new MaintenanceInput();
        save = new Save();
        inputvalidate = new InputValidate();
        
        for (String action : new String[] {CREATE, EDIT, DISPLAY}) {
            if (extcontext.number != null && action.equals(CREATE))
                continue;
            
            entityaction = name.concat(action);
            viewctx = context.instance(entityaction);
            viewctx.set(selspec);
            viewctx.set(new SelectConfig(action, extcontext.cmodel));
            viewctx.set(extcontext);
            switch (action) {
            case "create":
                viewctx.put(CREATE, new Validate());
                break;
            case "edit":
                viewctx.put(EDIT, new Load(edit1));
                break;
            case "display":
                viewctx.put(DISPLAY, new Load(display1));
                break;
            }
        }
        
        for (String view : new String[] {create, create1, edit1}) {
            if (extcontext.number == null && view.equals(create))
                continue;
            
            viewctx = context.instance(view);
            viewctx.set(maintenancespec);
            viewctx.set(maintenanceconfig);
            viewctx.set(maintenanceinput);
            viewctx.set(extcontext);
            viewctx.put("validate", inputvalidate);
            viewctx.put("save", save);
        }

        viewctx = context.instance(display1);
        viewctx.set(maintenancespec);
        viewctx.set(new DisplayConfig());
        viewctx.set(maintenanceinput);
        viewctx.setUpdate(true);
        viewctx.set(extcontext);
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

class InputValidate extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception { }
    
}