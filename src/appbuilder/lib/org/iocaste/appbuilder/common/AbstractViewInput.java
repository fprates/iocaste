package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.TableTool;

public abstract class AbstractViewInput {
    private PageBuilderContext context;
    private Manager manager;
    
    protected final void addTableItems(String table) {
        addTableItems(table, null);
    }
    
    protected final void addTableItems(String table, ExtendedObject[] objects) {
        TableTool tabletool = getViewComponents().tabletools.get(table);
        
        if (tabletool == null)
            throw new RuntimeException(table.
                    concat(" is an invalid tabletool."));
        
        tabletool.setObjects(objects);
    }
    
    protected abstract void execute(PageBuilderContext context);
    
    protected <T extends Element> T getElement(String name) {
        return context.view.getElement(name);
    }
    
    protected DashboardComponent getDashboardItem(String dashboard, String name)
    {
        return getViewComponents().dashboards.get(dashboard).get(name);
    }
    
    @SuppressWarnings("unchecked")
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getExtendedContext(context.view.getPageName());
    }
    
    private InputComponent getinput(String form, String item) {
        return ((DataForm)context.view.getElement(form)).get(item);
    }
    
    protected final Manager getManager() {
        return manager;
    }
    
    private final ViewComponents getViewComponents() {
        return context.getViewComponents(context.view.getPageName());
    }
    
    public final void run(PageBuilderContext context) {
        this.context = context;
        execute(context);
    }
    
    protected final void set(String form, String item, Object value) {
        getinput(form, item).set(value);
    }
    
    protected final void setdf(String form, ExtendedObject object) {
        ((DataForm)context.view.getElement(form)).setObject(object);
    }
    
    protected final void setdfkey(String form, Object value) {
        DocumentModel model = manager.getModel().getHeader();
        
        for (DocumentModelKey key : model.getKeys()) {
            getinput(form, key.getModelItemName()).set(value);
            break;
        }
    }
    
    public final void setManager(Manager manager) {
        this.manager = manager;
    }
}
