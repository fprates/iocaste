package org.iocaste.runtime.common.page;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.application.ToolData;

public abstract class AbstractViewInput<C extends Context> implements ViewInput
{
    private Context context;
    private boolean init;
    private String prefix;
//    
//    private final void addtablearray(
//            String table, ExtendedObject[] objects) {
//        ((TableToolData)getComponentData(table)).set(objects);
//    }
//    
//    private final void addtablecollection(
//            String table, Collection<ExtendedObject> objects) {
//        ((TableToolData)getComponentData(table)).set(objects);
//    }
//    
//    private final void addtableitems(
//            String table, List<TableToolItem> items) {
//        ((TableToolData)getComponentData(table)).set(items);
//    }
    
    protected final void listset(String tooldata,
    		String item, ExtendedObject[] objects) {
        listset(tooldata, item, objects, item);
    }
    
    protected final void listset(String tooldata,
    		String item, ExtendedObject[] objects, String field) {
        Object value;
        ToolData toolitem = getComponentData(tooldata).instance(item);
        
        if (toolitem.values == null)
        	toolitem.values = new LinkedHashMap<>();
        
        for (ExtendedObject object : objects) {
            value = object.get(field);
            toolitem.values.put(value.toString(), value);
        }
    }
    
    protected final void listset(String tooldata,
    		String item, Map<String, Object> values) {
        getComponentData(tooldata).instance(item).values = values;
    }
    
    protected final void inputset(String tooldata, String item, Object value) {
        getComponentData(tooldata).object.set(item, value);
    }
    
    protected final void inputset(String tooldata, ExtendedObject object) {
        getComponentData(tooldata).object = object;
    }
//    
//    protected final void dfkeyset(String form, Object value) {
//        DocumentModel model = null;
//        DataFormToolData df = getComponentData(form);
//        
//        if (df.custommodel != null)
//            model = df.custommodel;
//        if (df.model != null)
//            model = new Documents(context.function).getModel(df.model);
//        
//        for (DocumentModelKey key : model.getKeys()) {
//            df.instance(key.getModelItemName()).value = value;
//            break;
//        }
//    }
//    
//    protected final void dfnsset(String form, Object ns) {
//        DataFormToolData df = getComponentData(form);
//        df.nsItemInstance().value = ns;
//    }
    
    protected abstract void execute(C context);
    
    private final ToolData getComponentData(String name) {
        ToolData data = context.getPage().getToolData(name);
        if (data != null)
            return data;
        throw new RuntimeException(name.concat(" is an invalid tooldata."));
    }
//    
//    private final ViewComponents getViewComponents() {
//        return context.getView(context.view.getPageName()).getComponents();
//    }
    
    protected abstract void init(C context);

    protected final void input(ViewInput input) {
        input.run(context, init);
    }
    
    protected final void inputset(String name, Object value) {
    	getComponentData(name).value = value;
    }
    
//    protected void listadd(String name, String text, Object value) {
//        ListBox input = getElement(name);
//        input.add(text, value);
//    }
//    
//    protected void linkadd(String name, ExtendedObject object, String field) {
//        DataElement delement = object.getModel().getModelItem(field).
//                getDataElement();
//        Link link = getElement(name);
//        String pname = new StringBuilder(name).append("_").append(field).
//                toString();
//        link.add(pname, object.get(field), delement.getType());
//    }
//    
//    protected void linkadd(String name, String parameter, Object value) {
//        Link link = getElement(name);
//        String pname = new StringBuilder(name).append("_").append(parameter).
//                toString();
//        
//        link.add(pname, (value == null)? null : value.toString());
//    }
    
    protected void listset(String name, Object[][] values) {
    	Map<String, Object> valuesmap = getComponentData(name).values;
    	
    	valuesmap.clear();
        for (int i = 0; i < values.length; i++)
        	valuesmap.put(values[i][0].toString(), values[i][1]);
    }
//    
//    protected void loadInputTexts(PageBuilderContext context) {
//        Set<Validator> validators;
//        AbstractExtendedValidator exvalidator;
//        Element element;
//        InputComponent input;
//        Map<String, Set<Validator>> validables;
//        
//        validables = context.getView().getValidables();
//        for (String name : context.view.getElements().keySet()) {
//            element = getElement(name);
//            if (!element.isDataStorable() || !element.isVisible())
//                continue;
//            
//            validators = validables.get(name);
//            if (validators == null)
//                continue;
//            
//            input = (InputComponent)element;
//            for (Validator validator : validators) {
//                exvalidator = (AbstractExtendedValidator)validator;
//                input.setText(exvalidator.getText(input.get()));
//            }
//        }
//    }
//    
//    protected final void print(Collection<String> lines) {
//        PrintArea area = getElement("printarea");
//        
//        if (lines == null) {
//            area.clear();
//            return;
//        }
//        for (String line : lines)
//            area.add(line);
//        area.commit();
//    }
//    
//    protected final void print(String... lines) {
//        PrintArea area = getElement("printarea");
//        
//        if (lines == null) {
//            area.clear();
//            return;
//        }
//        for (String line : lines)
//            area.add(line);
//        area.commit();
//    }
//    
//    protected final void printclear() {
//        PrintArea area = getElement("printarea");
//        area.clear();
//    }
//    
//    protected final void reportlistset(
//            String report, String field, Map<String, Object> values) {
//        ((ReportToolData)
//                getComponentData(report)).input.items.get(
//                        field).values = values;
//    }
//    
//    protected final void reportlistset(
//            String report, String field, ExtendedObject[] objects) {
//        Map<String, Object> values;
//        Object value;
//        values = new LinkedHashMap<>();
//        for (ExtendedObject object : objects) {
//            value = object.get(field);
//            values.put(value.toString(), value);
//        }
//        ((ReportToolData)getComponentData(report)).input.items.get(
//                field).values = values;
//    }
//    
//    protected final void reportset(String report, ExtendedObject object) {
//        ((ReportToolData)getComponentData(report)).input.object = object;
//    }
//    
//    protected final void reportset(String report, ExtendedObject[] items) {
//        ((ReportToolData)getComponentData(report)).output.objects = items;
//    }
//    
//    protected final void reportset(
//            String report, Collection<ExtendedObject> items) {
//        reportset(report, items.toArray(new ExtendedObject[0]));
//    }
//    
    @Override
    public final void run(Context context, boolean init) {
        run(context, init, null);
    }
    
	@Override
    @SuppressWarnings("unchecked")
    public final void run(Context context, boolean init,
            String prefix) {
        this.context = context;
        this.init = init;
        this.prefix = prefix;
        if (init)
            init((C)context);
        else
            execute((C)context);
    }
//    
//    protected final void tableclear(String table) {
//        ((TableToolData)getComponentData(table)).clear();
//    }
//    
//    protected final TableToolItem tableitemadd(
//            String table, ExtendedObject object) {
//        TableToolItem item = ((TableToolData)getComponentData(table)).
//                add(object);
//        return item;
//    }
//    
//    protected final void tableitemsadd(String table) {
//        addtablecollection(table, null);
//    }
//    
//    protected final TableToolItem tableitemset(
//            String table, int index, ExtendedObject object) {
//        TableToolData data = getComponentData(table);
//        return data.set(index, object);
//    }
//    
//    protected final void tableitemsset(String table, ExtendedObject[] objects) {
//        addtablearray(table, objects);
//    }
//    
//    protected final void tableitemsset(
//            String table, Collection<ExtendedObject> objects) {
//        addtablecollection(table, objects);
//    }
//    
//    protected final void tableitemsset(String table, List<TableToolItem> items)
//    {
//        addtableitems(table, items);
//    }
//    
//    protected final void tableitemsset(String table, DataConversion conversion)
//    {
//        Documents documents = new Documents(context.function);
//        ExtendedObject[] objects = DocumentExtractor.extractItems(
//                context, null, documents, conversion, null, null);
//        
//        addtablearray(table, objects);
//    }
//    
//    protected final void texteditorset(String texteditor, String text) {
//        TextEditor editor = getViewComponents().editors.get(texteditor);
//        editor.getElement(context).set(text);
//    }
    
    protected final void textset(String name, String text, Object... args) {
    	ToolData tooldata = getComponentData(name);
    	tooldata.text = text;
    	tooldata.textargs = args;
    }
    
//    @SuppressWarnings("unchecked")
//    protected final <T> T tilesobjectget() {
//        ExtendedContext extcontext = getExtendedContext();
//        return (T)extcontext.tilesobjectget(extcontext.parentget(prefix));
//    }
//    
//    protected final void tileactionset(Object action) {
//        ExtendedContext extcontext = getExtendedContext();
//        extcontext.tilesactionset(
//                extcontext.parentget(prefix), action.toString());
//    }
//    
//    protected final void tilesset(String name, Object[] objects) {
//        ((TilesData)getComponentData(name)).objects = objects;
//    }
//    
//    protected final void tilesset(String name, Collection<?> objects) {
//        tilesset(name, objects.toArray());
//    }
}
