package org.iocaste.shell.common;

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
    
    public TableItem(Table table) {
        super(table, Const.TABLE_ITEM, new StringBuilder(table.getName()).
                append(".").append(table.getLength()).toString());
        String name_ = new StringBuilder(getName()).append(".mark").toString();
        
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
     * @return
     */
    public final ExtendedObject getObject() {
        InputComponent input;
        DocumentModelItem modelitem;
        Table table = (Table)getContainer();
        ExtendedObject object = new ExtendedObject(table.getModel());
        
        for (Element element : table.getElements()) {
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
    public final String[] getElementNames() {
        return elements;
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
}
