package org.iocaste.shell.common;

public class Form extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private String action;
    private Table table;
    private Button button;
    private MessageSource messages;
    
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
    
    public final void build() {
        TableItem item;
        Text text;
        
        button.setName(action);
        button.setText(getMessage(messages, action));
        
        for (Element line : table.getElements()) {
            item = (TableItem)line;
            
            for (Element column : item.getElements()) {
                text = (Text)column;
                text.setText(getMessage(messages, text.getName()));
                break;
            }
        }
    }
    
    public final String getAction() {
        return action;
    }
    
    private final String getMessage(MessageSource message, String name) {
        return (messages == null)?name : messages.get(name, name);
    }
    
    public final void setAction(String action) {
        this.action = action;
    }
    
    /**
     * 
     * @param messages
     */
    public final void setMessageSource(MessageSource messages) {
        this.messages = messages;
    }
}
