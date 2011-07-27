package org.iocaste.shell.common;

import java.util.ArrayList;
import java.util.List;

public class Form extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private List<String> actions;
    private Table table;
    private MessageSource messages;
    
    public Form(Container container) {
        super(container, Const.FORM);
        table = new Table(this, 2);
        actions = new ArrayList<String>();
    }
    
    public final void addItem(FormItem item) {
        TableItem tableitem = new TableItem(table);
        
        tableitem.add(item.getText());
        tableitem.add(item.getComponent());
    }
    
    public final void build() {
        Button button;
        TableItem item;
        Text text;
        
        for (String action : actions) {
            button = new Button(this);
            button.setSubmit(true);
            button.setName(action);
            button.setText(getMessage(messages, action));
        }
        
        for (Element line : table.getElements()) {
            item = (TableItem)line;
            
            for (Element column : item.getElements()) {
                text = (Text)column;
                text.setText(getMessage(messages, text.getName()));
                break;
            }
        }
    }
    
    public final String[] getActions() {
        return actions.toArray(new String[0]);
    }
    
    private final String getMessage(MessageSource message, String name) {
        return (messages == null)?name : messages.get(name, name);
    }
    
    public final void addAction(String action) {
        actions.add(action);
    }
    
    /**
     * 
     * @param messages
     */
    public final void setMessageSource(MessageSource messages) {
        this.messages = messages;
    }
}
