package org.iocaste.appbuilder.common;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.PrintArea;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.Validator;
import org.iocaste.texteditor.common.TextEditor;

public abstract class AbstractViewInput implements ViewInput {
    private PageBuilderContext context;
    private boolean init;
    
    private final void addtablearray(
            String table, ExtendedObject[] objects) {
        ((TableToolData)getComponentData(table)).set(objects);
    }
    
    private final void addtablecollection(
            String table, Collection<ExtendedObject> objects) {
        ((TableToolData)getComponentData(table)).set(objects);
    }
    
    private final void addtableitems(
            String table, List<TableToolItem> items) {
        ((TableToolData)getComponentData(table)).set(items);
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
        dbget(dashboard, name).add(value, value, null);
    }
    
    protected void dbitemadd(String dashboard, String name, byte value) {
        dbget(dashboard, name).add(Byte.toString(value), value, null);
    }
    
    protected void dbitemadd(String dashboard, String name, int value) {
        dbget(dashboard, name).add(Integer.toString(value), value, null);
    }
    
    protected void dbitemadd(String dashboard, String name, long value) {
        dbget(dashboard, name).add(Long.toString(value), value, null);
    }
    
    protected void dbitemadd(String dashboard, String name, short value) {
        dbget(dashboard, name).add(Short.toString(value), value, null);
    }
    
    protected void dbitemadd(String dashboard, String name, String key,
            byte value) {
        dbget(dashboard, name).add(key, value, null);
    }
    
    protected void dbitemadd(String dashboard, String name, String key,
            int value) {
        dbget(dashboard, name).add(key, value, null);
    }
    
    protected void dbitemadd(String dashboard, String name, String key,
            long value) {
        dbget(dashboard, name).add(key, value, null);
    }
    
    protected void dbitemadd(String dashboard, String name, String key,
            String value) {
        dbget(dashboard, name).add(key, value, null);
    }
    
    protected void dbitemadd(String dashboard, String name, String key,
            String value, String text) {
        dbget(dashboard, name).add(key, value, text);
    }
    
    protected final void dflistset(
            String form, String item, ExtendedObject[] objects) {
        dflistset(form, item, objects, item);
    }
    
    protected final void dflistset(
            String form, String item, ExtendedObject[] objects, String field) {
        Object value;
        DataFormToolItem dfitem = ((DataFormToolData)getComponentData(form)).
                itemInstance(item);
        
        if (dfitem.values == null)
            dfitem.values = new LinkedHashMap<>();
        
        for (ExtendedObject object : objects) {
            value = object.get(field);
            dfitem.values.put(value.toString(), value);
        }
    }
    
    protected final void dflistset(
            String form, String item, Map<String, Object> values) {
        ((DataFormToolData)
                getComponentData(form)).itemInstance(item).values = values;
    }
    
    protected final void dfset(String form, String item, Object value) {
        ((DataFormToolData)
                getComponentData(form)).itemInstance(item).value = value;
    }
    
    protected final void dfset(String form, ExtendedObject object) {
        ((DataFormToolData)getComponentData(form)).object = object;
        
    }
    
    protected final void dfkeyset(String form, Object value) {
        DocumentModel model = null;
        DataFormToolData df = getComponentData(form);
        
        if (df.model != null)
            model = df.model;
        if (df.modelname != null)
            model = new Documents(context.function).getModel(df.modelname);
        
        for (DocumentModelKey key : model.getKeys()) {
            df.itemInstance(key.getModelItemName()).value = value;
            break;
        }
    }
    
    protected final void dfnsset(String form, Object ns) {
        DataFormToolData df = getComponentData(form);
        df.nsItemInstance().value = ns;
    }
    
    protected abstract void execute(PageBuilderContext context);
    
    @SuppressWarnings("unchecked")
    private final <T extends AbstractComponentData> T getComponentData(
            String name) {
        AbstractComponentData data = getViewComponents().getComponentData(name);
        if (data != null)
            return (T)data;
        throw new RuntimeException(name.concat(" is an invalid tabletool."));
    }
    
    private <T extends Element> T getElement(String name) {
        return context.view.getElement(name);
    }
    
    @SuppressWarnings("unchecked")
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getView().getExtendedContext();
    }
    
    protected final Manager getManager(String name) {
        return context.getManager(name);
    }
    
    private final ViewComponents getViewComponents() {
        return context.getView(context.view.getPageName()).getComponents();
    }
    
    protected abstract void init(PageBuilderContext context);

    protected final void input(ViewInput input) {
        input.run(context, init);
    }
    
    protected final void inputset(String name, Object value) {
        InputComponent input = getElement(name);
        input.set(value);
    }
    
    protected void listadd(String name, String text, Object value) {
        ListBox input = getElement(name);
        input.add(text, value);
    }
    
    protected void loadInputTexts(PageBuilderContext context) {
        Set<Validator> validators;
        AbstractExtendedValidator exvalidator;
        Element element;
        InputComponent input;
        
        for (String name : context.view.getElements().keySet()) {
            element = getElement(name);
            
            if (!element.isDataStorable() || !element.isVisible())
                continue;
            
            validators = context.function.getValidables().get(name);
            if (validators == null)
                continue;
            
            input = (InputComponent)element;
            for (Validator validator : validators) {
                exvalidator = (AbstractExtendedValidator)validator;
                input.setText(exvalidator.getText(input.get()));
            }
        }
    }
    
    protected final void print(String line) {
        PrintArea area;
        
        if (line == null)
            return;
        
        area = getElement("printarea");
        area.add(line);
    }
    
    protected final void reportlistset(
            String report, String field, Map<String, Object> values) {
        ((ReportToolData)
                getComponentData(report)).input.items.get(
                        field).values = values;
    }
    
    protected final void reportlistset(
            String report, String field, ExtendedObject[] objects) {
        Map<String, Object> values;
        Object value;
        values = new LinkedHashMap<>();
        for (ExtendedObject object : objects) {
            value = object.get(field);
            values.put(value.toString(), value);
        }
        ((ReportToolData)getComponentData(report)).input.items.get(
                field).values = values;
    }
    
    protected final void reportset(String report, ExtendedObject object) {
        ((ReportToolData)getComponentData(report)).input.object = object;
    }
    
    protected final void reportset(String report, ExtendedObject[] items) {
        ((ReportToolData)getComponentData(report)).output.objects = items;
    }
    
    protected final void reportset(
            String report, Collection<ExtendedObject> items) {
        reportset(report, items.toArray(new ExtendedObject[0]));
    }
    
    @Override
    public final void run(PageBuilderContext context, boolean init) {
        this.context = context;
        this.init = init;
        if (init)
            init(context);
        else
            execute(context);
    }
    
    protected final void tableclear(String table) {
        ((TableToolData)getComponentData(table)).clear();
    }
    
    protected final TableToolItem tableitemadd(
            String table, ExtendedObject object) {
        TableToolItem item = ((TableToolData)getComponentData(table)).
                add(object);
        return item;
    }
    
    protected final void tableitemsadd(String table) {
        addtablecollection(table, null);
    }
    
    protected final TableToolItem tableitemset(
            String table, int index, ExtendedObject object) {
        TableToolData data = getComponentData(table);
        return data.set(index, object);
    }
    
    protected final void tableitemsset(String table, ExtendedObject[] objects) {
        addtablearray(table, objects);
    }
    
    protected final void tableitemsset(
            String table, Collection<ExtendedObject> objects) {
        addtablecollection(table, objects);
    }
    
    protected final void tableitemsset(String table, List<TableToolItem> items)
    {
        addtableitems(table, items);
    }
    
    protected final void tableitemsset(String table, DataConversion conversion)
    {
        Documents documents = new Documents(context.function);
        ExtendedObject[] objects = DocumentExtractor.extractItems(
                context, null, documents, conversion, null, null);
        
        addtablearray(table, objects);
    }
    
    protected final void texteditorset(String texteditor, String text) {
        TextEditor editor = getViewComponents().editors.get(texteditor);
        editor.getElement(context).set(text);
    }
    
    protected final void textset(String name, String text) {
        Text element = getElement(name);
        element.setText(text);
    }
}
