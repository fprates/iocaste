package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.TableTool;

public abstract class AbstractViewInput {
    private PageBuilderContext context;
    private Manager manager;
    private boolean updated;
    private Map<String, Object> storage;
    
    public AbstractViewInput() {
        storage = new HashMap<>();
    }
    
    private final void addtableitems(String table, ExtendedObject[] objects) {
        TableTool tabletool = getViewComponents().tabletools.get(table);
        
        if (tabletool == null)
            throw new RuntimeException(table.
                    concat(" is an invalid tabletool."));
        
        tabletool.setObjects(objects);
    }
    
    protected final void addTableItems(String table) {
        addtableitems(table, null);
    }
    
    protected final void addTableItems(String table, ExtendedObject[] objects) {
        TableTool tabletool = getViewComponents().tabletools.get(table);
        
        if (tabletool == null)
            throw new RuntimeException(table.
                    concat(" is an invalid tabletool."));
        
        tabletool.setObjects(objects);
    }
    
    protected final void addTableItems(String table, DataConversion conversion)
    {
        Documents documents = new Documents(context.function);
        ExtendedObject[] objects = DocumentExtractor.extractItems(
                documents, conversion, null, null);
        
        addtableitems(table, objects);
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
        AbstractViewSpec spec;
        ViewSpecItem.TYPES[] types;
        DataForm dataform;
        ViewComponents components;
        InputComponent input;
        
        this.context = context;
        execute(context);
        if (!updated)
            return;

        types = ViewSpecItem.TYPES.values();
        spec = context.getViewSpec(context.view.getPageName());
        components = getViewComponents();
        for (String name : storage.keySet()) {
            switch (types[spec.get(name).getType()]) {
            case DATA_FORM:
                dataform = getElement(name);
                dataform.setObject((ExtendedObject)storage.get(name));
                break;
            case TABLE_TOOL:
                components.tabletools.get(name).setObjects(
                        (ExtendedObject[])storage.get(name));
                break;
            default:
                input = getElement(name);
                input.set(storage.get(name));
                break;
            }
        }
        storage.clear();
        updated = false;
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
    
    public final void setUpdated(boolean updated) {
        this.updated = updated;
    }
    
    public final void store(String name, Object value) {
        storage.put(name, value);
    }
}
