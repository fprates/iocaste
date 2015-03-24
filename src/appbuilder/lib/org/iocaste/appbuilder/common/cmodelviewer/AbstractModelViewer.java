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
    private Context extcontext;
    
    protected final AppBuilderLink getReceivedLink() {
        AppBuilderLink link;
        String entity = getParameter("name");
        
        if (entity == null)
            return null;
        
        link = new AppBuilderLink();
        link.entity = entity;
        link.cmodel = getParameter("cmodel");
        link.number = getParameter("number");
        link.numberseries = getParameter("numberseries");
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
        AbstractViewSpec selspec;
        AbstractActionHandler inputvalidate;
        String entityaction;
        Manager manager;
        
        if (extcontext == null)
            extcontext = new Context();
        
        extcontext.link = link;
        extcontext.redirect = (link.number == null)?
                link.create1view : link.edit1view;
        
        manager = managerInstance(link.cmodel);
        context.addManager(link.cmodel, manager);
        
        selspec = new SelectSpec();
        inputvalidate = new InputValidate();
        
        for (String action : new String[] {CREATE, EDIT, DISPLAY}) {
            if ((link.number != null) && action.equals(CREATE) &&
                    (link.createselectconfig == null))
                continue;
            
            entityaction = link.entity.concat(action);
            viewctx = context.instance(entityaction);
            viewctx.set(selspec);
            viewctx.set(extcontext);
            switch (action) {
            case CREATE:
                setSelectConfig(link.createselectconfig, viewctx, action,
                        extcontext, link);
                viewctx.put(CREATE, link.validate);
                
                break;
            case EDIT:
                setSelectConfig(link.updateselectconfig, viewctx, action,
                        extcontext, link);
                
                viewctx.put(EDIT, link.updateload);
                break;
            case DISPLAY:
                setSelectConfig(link.displayselectconfig, viewctx, action,
                        extcontext, link);
                
                viewctx.put(DISPLAY, link.displayload);
                break;
            }
        }
        
        for (String view : new String[] {
                link.createview, link.create1view, link.edit1view}) {
            if (view.equals(link.createview) &&
                    ((link.number == null) ||
                            (link.createselectconfig != null)))
                continue;
            
            viewctx = context.instance(view);
            viewctx.set(link.maintenancespec);
            viewctx.set(link.maintenanceconfig);
            viewctx.set(link.maintenanceinput);
            viewctx.set(extcontext);
            viewctx.put("validate", inputvalidate);
            viewctx.put("save", link.save);
        }

        viewctx = context.instance(link.display1view);
        viewctx.set(link.maintenancespec);
        viewctx.set(link.displayconfig);
        viewctx.set(link.maintenanceinput);
        viewctx.set(extcontext);
    }
    
    private final Manager managerInstance(String cmodel) {
        return new RuntimeManager(cmodel, this);
    }
    
    /**
     * 
     * @param extcontext
     */
    protected final void setExtendedContext(Context extcontext) {
        this.extcontext = extcontext;
    }
    
    private final void setSelectConfig(ViewConfig config, ViewContext viewctx,
            String action, Context context, AppBuilderLink link) {
        if (config == null)
            viewctx.set(new SelectConfig(action, link.cmodel));
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