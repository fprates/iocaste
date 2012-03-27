package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

/**
 * 
 * @author Francisco Prates
 *
 */
public class TableItem implements Serializable {
    private static final long serialVersionUID = -1076760582954115701L;
    private Map<String, Element> elements;
    private TableColumn[] columns;
    private Table table;
    private Locale locale;
    
    public TableItem(Table table) {
        table.add(this);
        
        this.table = table;
        columns = table.getColumns();
        elements = new LinkedHashMap<String, Element>();
        elements.put("mark", new CheckBox(table, "mark"));
    }
    
    /**
     * 
     * @param element
     */
    public final void add(Element element) {
        int i = elements.size();
        
        if (i == table.width())
            throw new RuntimeException("Item overflow for table.");
        
        elements.put(columns[i].getName(), element);
        element.setLocale(locale);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T extends Element> T get(String name) {
        return (T)elements.get(name);
    }
    
    /**
     * 
     * @return
     */
    public final Element[] getElements() {
        return elements.values().toArray(new Element[0]);
    }
    
    /**
     * 
     * @return
     */
    public final Locale getLocale() {
        return locale;
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public final ExtendedObject getObject() throws Exception {
        Element element;
        InputComponent input;
        DocumentModelItem modelitem;
        ExtendedObject object = new ExtendedObject(table.getModel());
        
        for (String name : elements.keySet()) {
            element = elements.get(name);
            
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            modelitem = input.getModelItem();
            
            if (modelitem == null)
                continue;
            
            object.setValue(modelitem, Shell.getInputValue(input));
        }
        
        return object;
    }
    
    /**
     * 
     * @return
     */
    public final Table getTable() {
        return table;
    }
    
    /**
     * 
     * @return
     */
    public final boolean isSelected() {
        CheckBox mark = (CheckBox)elements.get("mark");
        
        return mark.isSelected();
    }
    
    /**
     * 
     * @param locale
     */
    public final void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    /**
     * 
     * @param object
     */
    public final void setObject(ExtendedObject object) {
        Element element;
        Object value;
        InputComponent input;
        DocumentModelItem modelitem;
        
        for (TableColumn column : columns) {
            if (column.isMark())
                continue;
            
            element = elements.get(column.getName());
            
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            modelitem = column.getModelItem();
            input.setModelItem(modelitem);
            
            if (modelitem == null)
                continue;
            
            value = object.getValue(modelitem);
            Shell.setInputValue(input, value);
        }
    }
    
    /**
     * 
     * @param selected
     */
    public final void setSelected(boolean selected) {
        CheckBox mark = (CheckBox)elements.get("mark");
        
        mark.setSelected(selected);
    }
}
