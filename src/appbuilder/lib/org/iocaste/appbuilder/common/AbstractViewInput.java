package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.texteditor.common.TextEditor;

public abstract class AbstractViewInput {
    private PageBuilderContext context;
    private boolean init;
    
    private final void addtableitems(String table, ExtendedObject[] objects) {
        TableToolEntry entry = getViewComponents().tabletools.get(table);
        TableToolData tabletool = entry.data;
        
        if (tabletool == null)
            throw new RuntimeException(table.
                    concat(" is an invalid tabletool."));
        
        tabletool.objects = objects;
        entry.update = !init;
    }
    
    private DashboardComponent dbget(String dashboard, String name) {
        ViewComponents components = getViewComponents();
        DashboardFactory factory = components.dashboards.get(dashboard);
        
        if (factory != null)
            return factory.get(name);
        
        factory = components.dashboardgroups.get(dashboard).getFactory();
        return factory.get(name);
    }
    
    protected void dbitemadd(String dashboard, String name, String value) {
        dbget(dashboard, name).add(value);
    }
    
    protected void dbitemadd(String dashboard, String name, byte value) {
        dbget(dashboard, name).add(Byte.toString(value), value);
    }
    
    protected void dbitemadd(String dashboard, String name, int value) {
        dbget(dashboard, name).add(Integer.toString(value), value);
    }
    
    protected void dbitemadd(String dashboard, String name, long value) {
        dbget(dashboard, name).add(Long.toString(value), value);
    }
    
    protected void dbitemadd(String dashboard, String name, short value) {
        dbget(dashboard, name).add(Short.toString(value), value);
    }
    
    protected void dbitemadd(String dashboard, String name, String key,
            byte value) {
        dbget(dashboard, name).add(key, value);
    }
    
    protected void dbitemadd(String dashboard, String name, String key,
            int value) {
        dbget(dashboard, name).add(key, value);
    }
    
    protected void dbitemadd(String dashboard, String name, String key,
            long value) {
        dbget(dashboard, name).add(key, value);
    }
    
    protected void dbitemadd(String dashboard, String name, String key,
            String value) {
        dbget(dashboard, name).add(key, value);
    }
    
    protected void dbtextadd(String dashboard, String name, String value) {
        dbget(dashboard, name).addText(value);
    }
    
    protected abstract void execute(PageBuilderContext context);
    
    protected <T extends Element> T getElement(String name) {
        return context.view.getElement(name);
    }
    
    @SuppressWarnings("unchecked")
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getView(context.view.getPageName()).
                getExtendedContext();
    }
    
    private InputComponent getinput(String form, String item) {
        return ((DataForm)context.view.getElement(form)).get(item);
    }
    
    protected final Manager getManager(String name) {
        return context.getManager(name);
    }
    
    private final ViewComponents getViewComponents() {
        return context.getView(context.view.getPageName()).getComponents();
    }
    
    protected abstract void init(PageBuilderContext context);
    
    protected final void reportset(String report, ExtendedObject[] items) {
        getViewComponents().reporttools.get(report).setItens(items);
    }
    
    public final void run(PageBuilderContext context, boolean init) {
        this.context = context;
        this.init = init;
        if (init)
            init(context);
        else
            execute(context);
    }
    
    protected final void set(String form, String item, Object value) {
        getinput(form, item).set(value);
    }
    
    protected final void setdf(String form, ExtendedObject object) {
        ((DataForm)context.view.getElement(form)).setObject(object);
    }
    
    protected final void setdfkey(String form, Object value) {
        DataForm df = context.view.getElement(form);
        DocumentModel model = df.getModel();
        
        for (DocumentModelKey key : model.getKeys()) {
            getinput(form, key.getModelItemName()).set(value);
            break;
        }
    }
    
    protected final void tableitemsadd(String table) {
        addtableitems(table, null);
    }
    
    protected final void tableitemsadd(String table, ExtendedObject[] objects) {
        addtableitems(table, objects);
    }
    
    protected final void tableitemsadd(String table, DataConversion conversion)
    {
        Documents documents = new Documents(context.function);
        ExtendedObject[] objects = DocumentExtractor.extractItems(
                documents, conversion, null, null);
        
        addtableitems(table, objects);
    }
    
    protected final void texteditorset(String texteditor, String text) {
        TextEditor editor = getViewComponents().editors.get(texteditor);
        editor.getElement().set(text);
    }
}
