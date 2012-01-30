package org.iocaste.shell.common;

import java.io.Serializable;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

/**
 * 
 * @author Francisco Prates
 *
 */
public class TableItem implements Serializable {
    private static final long serialVersionUID = -1076760582954115701L;
    private Element[] elements;
    private int i;
    private Table table;
    
    public TableItem(Table table) {
        table.add(this);
        this.table = table;
        i = 1;
        elements = new Element[table.width()];
        elements[0] = new CheckBox(table, "mark");
    }
    
    /**
     * 
     * @param element
     */
    public final void add(Element element) {
        if (i == elements.length)
            throw new RuntimeException("Item overflow for table.");
        
        elements[i++] = element;
    }
    
//    /**
//     * 
//     * @param name
//     * @return
//     */
//    public final Element getElement(String name) {
//        return table.getElement(getComplexName(name));
//    }
    
    /**
     * 
     * @return
     */
    public final Element[] getElements() {
        return elements;
    }
    
    /**
     * 
     * @return
     */
    public final ExtendedObject getObject() {
        InputComponent input;
        DocumentModelItem modelitem;
        ExtendedObject object = new ExtendedObject(table.getModel());
        
        for (Element element : elements) {
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
    
    /**
     * 
     * @return
     */
    public final boolean isSelected() {
        CheckBox mark = (CheckBox)elements[0];
        
        return mark.isSelected();
    }
    
    /**
     * 
     * @param object
     */
    public final void setObject(ExtendedObject object) {
        Object value;
        InputComponent input;
        DocumentModelItem modelitem;
        
        for (Element element : elements) {
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
