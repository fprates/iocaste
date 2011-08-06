package org.iocaste.shell.common;

public class TableItem extends AbstractComponent {
    private static final long serialVersionUID = -1076760582954115701L;
    private Element[] elements;
    private int i;
    
    public TableItem(Table table, String name) {
        super(table, Const.TABLE_ITEM, name);
        i = 0;
        elements = new Element[table.getWidth()];
    }
    
    public final void add(Element element) {
        if (i == elements.length)
            throw new RuntimeException("Item overflow for table.");
        
        elements[i++] = element;
    }
    
    public final Element[] getElements() {
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
