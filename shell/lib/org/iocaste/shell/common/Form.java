package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;

public class Form extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private List<String> actions;
    private Map<String, FormItem> itens;
    
    public Form(Container container, String name) {
        super(container, Const.FORM, name);
        
        new Parameter(this, "action");
        actions = new ArrayList<String>();
        itens = new HashMap<String, FormItem>();
    }
    
    /**
     * 
     * @param action
     */
    public final void addAction(String action) {
        actions.add(action);
    }
    
    /**
     * 
     * @param item
     */
    public final void addItem(FormItem item) {
        itens.put(item.getSimpleName(), item);
    }
    
    /**
     * 
     * @param object
     * @throws RuntimeException
     */
    public final void exportTo(Object object) throws RuntimeException {
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
    
    /**
     * 
     * @param class_
     * @param object
     * @param method
     * @param component
     * @throws RuntimeException
     */
    private final void invokeCopy(Class<?> class_, Object object,
            Method method, InputComponent component) throws RuntimeException {
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
            throw new RuntimeException(value, e);
        }
    }
    
    /**
     * 
     * @return
     */
    public final String[] getActions() {
        return actions.toArray(new String[0]);
    }
    
    /**
     * 
     * @param document
     */
    public final void importModel(DocumentModel document) {
        Set<DocumentModelItem> itens = document.getItens();
        
        for (DocumentModelItem item : itens)
            new FormItem(this, Const.TEXT_FIELD, item.getName());
    }
}
