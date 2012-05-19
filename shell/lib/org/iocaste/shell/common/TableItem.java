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
    private boolean visible;
    
    public TableItem(Table table) {
        String markname;
        RadioButton mark;
        table.add(this);
        
        this.table = table;
        columns = table.getColumns();
        elements = new LinkedHashMap<String, Element>();
        visible = true;
        
        switch (table.getSelectionType()) {
        case Table.SINGLE:
            markname = new StringBuilder(table.getName()).append(".").
                    append(table.length() - 1).append(".mark").toString();
            mark = new RadioButton(table, markname, table.getGroup());
            
            elements.put("mark", mark);
            break;
            
        case Table.MULTIPLE:
            elements.put("mark", new CheckBox(table, "mark"));
            break;
        }
    }
    
    /**
     * 
     * @param element
     */
    public final void add(Element element) {
        DocumentModelItem modelitem;
        int i = elements.size();
        
        if (i == table.width())
            throw new RuntimeException("Item overflow for table.");
        
        elements.put(columns[i].getName(), element);
        element.setLocale(locale);
        
        modelitem = columns[i].getModelItem();
        if (element.isDataStorable() && modelitem != null)
            ((InputComponent)element).setModelItem(modelitem);
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
     */
    public final ExtendedObject getObject() {
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
            
            object.setValue(modelitem, input.get());
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
        InputComponent input = (InputComponent)elements.get("mark");
        
        return input.isSelected();
    }
    
    /**
     * 
     * @return
     */
    public final boolean isVisible() {
        return visible;
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
            if (input.getModelItem() == null)
                input.setModelItem(modelitem);
            
            if (modelitem == null)
                continue;
            
            value = object.getValue(modelitem);
            input.set(value);
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
    
    /**
     * 
     * @param visible
     */
    public final void setVisible(boolean visible) {
        this.visible = visible;
    }
}
