package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.docmanager.common.AbstractManager;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.protocol.Function;

public abstract class AbstractModelViewer extends AbstractPageBuilder {
    public static final String CREATE = "create";
    public static final String DISPLAY = "display";
    public static final String EDIT = "edit";
    
    protected final AppBuilderLink getReceivedLink() {
        AppBuilderLink link;
        String entity = getParameter("name");
        
        if (entity == null)
            return null;
        
        link = new AppBuilderLink();
        link.entity = entity;
        link.cmodel = getParameter("cmodel");
        link.number = getParameter("number");
        link.createview = link.entity.concat(CREATE);
        link.create1view = link.createview.concat("1");
        link.editview = link.entity.concat(EDIT);
        link.edit1view = link.editview.concat("1");
        link.displayview = link.entity.concat(DISPLAY);
        link.display1view = link.displayview.concat("1");
        
        return link;
    }
    
    @Override
    protected abstract void installConfig(
            PageBuilderDefaultInstall defaultinstall);
    
    protected final void loadManagedModule(PageBuilderContext context,
            AppBuilderLink link) {
        ViewContext viewctx;
        AbstractViewSpec selspec, maintenancespec;
        AbstractActionHandler save, inputvalidate;
        String entityaction;
        Manager manager;
        
        link.extcontext.number = link.number;
        link.extcontext.cmodel = link.cmodel;
        link.extcontext.redirect = (link.extcontext.number == null)?
                link.create1view : link.edit1view;
        
        manager = managerInstance(link.extcontext.cmodel);
        context.addManager(link.extcontext.cmodel, manager);
        
        selspec = new SelectSpec();
        maintenancespec = new MaintenanceSpec();
        save = new Save();
        inputvalidate = new InputValidate();
        
        for (String action : new String[] {CREATE, EDIT, DISPLAY}) {
            if ((link.extcontext.number != null) && action.equals(CREATE) &&
                    (link.createselectconfig == null))
                continue;
            
            entityaction = link.entity.concat(action);
            viewctx = context.instance(entityaction);
            viewctx.set(selspec);
            viewctx.set(link.extcontext);
            switch (action) {
            case CREATE:
                setSelectConfig(link.createselectconfig, viewctx, action,
                        link.extcontext);
                viewctx.put(CREATE, link.validate);
                
                break;
            case EDIT:
                setSelectConfig(link.updateselectconfig, viewctx, action,
                        link.extcontext);
                
                viewctx.put(EDIT, link.updateload);
                break;
            case DISPLAY:
                setSelectConfig(link.displayselectconfig, viewctx, action,
                        link.extcontext);
                
                viewctx.put(DISPLAY, link.displayload);
                break;
            }
        }
        
        for (String view : new String[] {
                link.createview, link.create1view, link.edit1view}) {
            if (view.equals(link.createview) &&
                    ((link.extcontext.number == null) ||
                            (link.createselectconfig != null)))
                continue;
            
            viewctx = context.instance(view);
            viewctx.set(maintenancespec);
            viewctx.set(link.maintenanceconfig);
            viewctx.set(link.maintenanceinput);
            viewctx.set(link.extcontext);
            viewctx.put("validate", inputvalidate);
            viewctx.put("save", save);
        }

        viewctx = context.instance(link.display1view);
        viewctx.set(maintenancespec);
        viewctx.set(link.displayconfig);
        viewctx.set(link.maintenanceinput);
        viewctx.set(link.extcontext);
    }
    
    private final Manager managerInstance(String cmodel) {
        return new RuntimeManager(cmodel, this);
    }
    
    private final void setSelectConfig(ViewConfig config, ViewContext viewctx,
            String action, Context context) {
        if (config == null)
            viewctx.set(new SelectConfig(action, context.cmodel));
        else
            viewctx.set(config);
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