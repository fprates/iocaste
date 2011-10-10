package org.iocaste.shell.common;

public class DataItem extends AbstractInputComponent {
    private static final long serialVersionUID = 3376883855229003535L;
    
    public DataItem(AbstractContainer container, Const type, String name) {
        super(container, Const.DATA_ITEM, type, name);
        
        setStyleClass("form");
    }
}
