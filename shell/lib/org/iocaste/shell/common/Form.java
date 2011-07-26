package org.iocaste.shell.common;

public class Form extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private String action;
    private Table table;
    private Button button;
    
    public Form(Container container) {
        super(container, Const.FORM);
        table = new Table(this, 2);
        
        button = new Button(this);
        button.setSubmit(true);
    }
    
    public final void addItem(FormItem item) {
        TableItem tableitem = new TableItem(table);
        
        tableitem.add(item.getText());
        tableitem.add(item.getComponent());
    }
    
    public final String getAction() {
        return action;
    }
    
    public final void setAction(String action) {
        button.setName(action);
        button.setText(action);
        
        this.action = action;
    }
}
