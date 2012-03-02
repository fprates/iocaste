package org.iocaste.shell.common;

public class DataItem extends AbstractInputComponent {
    private static final long serialVersionUID = 3376883855229003535L;
    
    public DataItem(DataForm form, Const type, String name) {
        super(form, Const.DATA_ITEM, type, name);
        
        form.add(this);
        
        setStyleClass("form");
        setLength(20);
    }
}
