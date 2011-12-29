package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

/**
 * 
 * @author Francisco Prates
 *
 */
public class TableItem extends AbstractComponent {
    private static final long serialVersionUID = -1076760582954115701L;
    private String[] elements;
    private int i;
    private Map<String, String> colmap;
    private Table table;
    
    public TableItem(Table table) {
        super(table, Const.TABLE_ITEM, new StringBuilder(table.getName()).
                append(".").append(table.getLength()).toString());
        String name_ = new StringBuilder(getName()).append(".mark").toString();
        
        this.table = table;
        colmap = new HashMap<String, String>();
        i = 1;
        elements = new String[table.getWidth()];
        new CheckBox(table, name_);
        elements[0] = name_;
    }
    
    /**
     * 
     * @param element
     */
    public final void add(Element element) {
        if (i == elements.length)
            throw new RuntimeException("Item overflow for table.");
        
        elements[i++] = element.getName();
    }
    
    /**
     * 
     * @param type
     * @param name
     */
    public final void add(Const type, String name, Object[] args) {
        String complexname;
        Element element;
        InputComponent input;
        
        if (i == elements.length)
            throw new RuntimeException("Item overflow for table.");
        
        if (colmap.containsKey(name))
            throw new RuntimeException("Column already exist for table item");
        
        complexname = new StringBuilder(name).append(".").append(
                table.getLength() - 1).toString();
        element = Shell.factory(table, type, complexname, args);
        if (element.isDataStorable()) {
            input = (InputComponent)element;
            input.setModelItem(table.getColumn(name).getModelItem());
        }
            
        colmap.put(name, complexname);
        elements[i++] = element.getName();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final String getComplexName(String name) {
        return colmap.get(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final Element getElement(String name) {
        return table.getElement(getComplexName(name));
    }
    
    /**
     * 
     * @return
     */
    public final String[] getElementNames() {
        return elements;
    }
    
    /**
     * 
     * @return
     */
    public final ExtendedObject getObject() {
        Element element;
        InputComponent input;
        DocumentModelItem modelitem;
        Table table = (Table)getContainer();
        ExtendedObject object = new ExtendedObject(table.getModel());
        
        for (String name: elements) {
            element = table.getElement(name);
            
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            modelitem = input.getModelItem();
            
            if (modelitem == null)
                continue;
            
            object.setValue(modelitem, input.getValue());
        }
        
        return object;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isControlComponent()
     */
    public final boolean isControlComponent() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
        return false;
    }
    
    /**
     * 
     * @return
     */
    public final boolean isSelected() {
        Table table = (Table)getContainer();
        CheckBox mark = (CheckBox)table.getElement(elements[0]);
        
        return mark.isSelected();
    }
    
    /**
     * 
     * @param object
     */
    public final void setObject(ExtendedObject object) {
        Object value;
        Element element;
        InputComponent input;
        DocumentModelItem modelitem;
        Table table = (Table)getContainer();
        
        for (String name : elements) {
            element = table.getElement(name);
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            modelitem = input.getModelItem();
            if (modelitem == null)
                continue;
            
            value = object.getValue(modelitem);
            input.setValue((value == null)?null : value.toString());
        }
    }
}
