package org.iocaste.appbuilder.common;

import java.util.Collection;
import java.util.List;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.Validator;
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
    
    protected final void dflistset(
            String form, String item, ExtendedObject[] objects) {
        dflistset(form, item, objects, item);
    }
    
    protected final void dflistset(
            String form, String item, ExtendedObject[] objects, String field) {
        Object value;
        DataItem input = getinput(form, item);
        
        for (ExtendedObject object : objects) {
            value = object.get(field);
            input.add(value.toString(), value);
        }
    }
    
    protected final void dfset(String form, String item, Object value) {
        getinput(form, item).set(value);
    }
    
    protected final void dfset(String form, ExtendedObject object) {
        ((DataForm)context.view.getElement(form)).setObject(object);
    }
    
    protected final void dfkeyset(String form, Object value) {
        DataForm df = context.view.getElement(form);
        DocumentModel model = df.getModel();
        
        for (DocumentModelKey key : model.getKeys()) {
            getinput(form, key.getModelItemName()).set(value);
            break;
        }
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
    
    @SuppressWarnings("unchecked")
    private <T extends InputComponent> T getinput(String form, String item) {
        return (T)((DataForm)context.view.getElement(form)).get(item);
    }
    
    protected final Manager getManager(String name) {
        return context.getManager(name);
    }
    
    private final ViewComponents getViewComponents() {
        return context.getView(context.view.getPageName()).getComponents();
    }
    
    protected abstract void init(PageBuilderContext context);

    protected final void inputset(String name, Object value) {
        InputComponent input = context.view.getElement(name);
        input.set(value);
    }
    
    protected void listadd(String name, String text, Object value) {
        ListBox input = context.view.getElement(name);
        input.add(text, value);
    }
    
    protected void loadInputTexts(PageBuilderContext context) {
        List<Validator> validators;
        AbstractExtendedValidator exvalidator;
        Element element;
        InputComponent input;
        
        for (String name : context.view.getElements().keySet()) {
            element = context.view.getElement(name);
            
            if (!element.isDataStorable() || !element.isVisible())
                continue;
            
            validators = context.function.getValidators(name);
            if (validators == null)
                continue;
            
            input = (InputComponent)element;
            for (Validator validator : validators) {
                exvalidator = (AbstractExtendedValidator)validator;
                input.setText(exvalidator.getText(context, input.get()));
            }
        }
    }
    
    protected final void reportset(String report, ExtendedObject[] items) {
        getViewComponents().reporttools.get(report).data.objects = items;
    }
    
    protected final void reportset(
            String report, Collection<ExtendedObject> items) {
        reportset(report, items.toArray(new ExtendedObject[0]));
    }
    
    public final void run(PageBuilderContext context, boolean init) {
        this.context = context;
        this.init = init;
        if (init)
            init(context);
        else
            execute(context);
    }
    
    protected final void tableitemsadd(String table) {
        addtableitems(table, null);
    }
    
    protected final void tableitemsadd(String table, ExtendedObject[] objects) {
        
        addtableitems(table, objects);
    }
    
    protected final void tableitemsadd(
            String table, Collection<ExtendedObject> objects) {
        addtableitems(table, objects.toArray(new ExtendedObject[0]));
    }
    
    protected final void tableitemsadd(String table, DataConversion conversion)
    {
        Documents documents = new Documents(context.function);
        ExtendedObject[] objects = DocumentExtractor.extractItems(
                null, documents, conversion, null, null);
        
        addtableitems(table, objects);
    }
    
    protected final void texteditorset(String texteditor, String text) {
        TextEditor editor = getViewComponents().editors.get(texteditor);
        editor.getElement(context).set(text);
    }
    
    protected final void textset(String name, String text) {
        Text element = context.view.getElement(name);
        element.setText(text);
    }
}
