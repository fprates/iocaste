package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private List<String> actions;
    private Table table;
    private MessageSource messages;
    private String name;
    private Map<String, FormItem> itens;
    
    public Form(Container container, String name) {
        super(container, Const.FORM);
        this.name = name;
        
        table = new Table(this, 2);
        actions = new ArrayList<String>();
        itens = new HashMap<String, FormItem>();
    }
    
    public final void addAction(String action) {
        actions.add(action);
    }
    
    public final void addItem(FormItem item) {
        TableItem tableitem = new TableItem(table);

        tableitem.add(item.getText());
        tableitem.add(item.getComponent());
        
        itens.put(item.getSimpleName(), item);
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
    
    public final void exportTo(Object object) throws Exception {
        FormItem item;
        String formmethodname;
        String objmethodname;
        Method method_;
        
        for (String name: itens.keySet()) {
            formmethodname = new StringBuffer("set").append(name).toString().
                    toLowerCase();
            method_ = null;
            
            for (Method method : object.getClass().getMethods()) {
                objmethodname = method.getName().toLowerCase();
                
                if (!objmethodname.equals(formmethodname))
                    continue;
                
                method_ = method;
                break;
            }
            
            if (method_ == null)
                continue;
        
            item = itens.get(name);
            for (Class<?> class_ : method_.getParameterTypes()) {
                invokeCopy(class_, object, method_, item);
                break;
            }
        }
    }
    
    private final void invokeCopy(
            Class<?> class_, Object object, Method method, InputComponent component)
            throws Exception {
        String value = component.getValue();
        String typename = class_.getSimpleName();
        
        try {
            if (typename.equals("String")) {
                method.invoke(object, value);
                return;
            }
            
            if (typename.equals("Integer")) {
                method.invoke(object, Integer.parseInt(value));
                return;
            }
            
            if (typename.equals("Boolean")) {
                method.invoke(object, Boolean.parseBoolean(value));
                return;
            }
            
            if (typename.equals("Character")) {
                method.invoke(object, value.toCharArray()[0]);
                return;
            }
            
            if (typename.equals("Short")) {
                method.invoke(object, Short.parseShort(value));
                return;
            }
            
            if (typename.equals("Float")) {
                method.invoke(object, Float.parseFloat(value));
                return;
            }
            
            if (typename.equals("Double")) {
                method.invoke(object, Double.parseDouble(value));
                return;
            }
            
            if (typename.equals("Byte")) {
                method.invoke(object, Byte.parseByte(value));
                return;
            }
        } catch (Exception e) {
            value = new StringBuffer("Error loading parameter for ").
                    append(method.getName()).append(".").toString();
            throw new Exception(value);
        }
    }
    
    public final String[] getActions() {
        return actions.toArray(new String[0]);
    }
    
    private final String getMessage(MessageSource message, String name) {
        return (messages == null)?name : messages.get(name, name);
    }
    
    public final String getName() {
        return name;
    }
    
    /**
     * 
     * @param messages
     */
    public final void setMessageSource(MessageSource messages) {
        this.messages = messages;
    }
}
