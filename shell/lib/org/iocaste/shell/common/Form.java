package org.iocaste.shell.common;

public class Form extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private String action;
    private Table table;
    
    public Form(Container container) {
        super(container, Const.FORM);
        table = new Table(this, 2);
    }
    
    public final void addItem(FormItem item) {
        TableItem tableitem = new TableItem(table);
        
        tableitem.add(item.getText());
        tableitem.add(item.getComponent());
    }
    
    public final String getAction() {
        return action;
    }
    
    public final String getSubmitText() {
        return action;
    }
    
    public final void setAction(String action) {
        this.action = action;
    }
}
