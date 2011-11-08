package org.iocaste.shell.common;

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
    public final String[] getElementNames() {
        return elements;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
        return false;
    }
}
