package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;

public class DataForm extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private List<String> actions;
    
    public DataForm(Container container, String name) {
        super(container, Const.DATA_FORM, name);
        
        actions = new ArrayList<String>();
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
     * @param object
     * @throws RuntimeException
     */
    public final void exportTo(Object object) throws RuntimeException {
        DataFormItem item;
        String formmethodname;
        String objmethodname;
        String name;
        Method method_;
        DocumentModelItem modelitem;
        
        for (Element element: getElements()) {
            if (!element.isDataStorable())
                continue;
            
            item = (DataFormItem)element;
            modelitem = item.getModelItem();

            if (modelitem == null)
                continue;
            
            name = modelitem.getName();
            
            formmethodname = new StringBuffer("set").
                    append(name).toString().toLowerCase();
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

            for (Class<?> class_ : method_.getParameterTypes()) {
                invokeCopy(class_, object, method_, item);
                break;
            }
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
     * @param model
     */
    public final void setModel(DocumentModel model) {
        String name;
        DataFormItem formitem;
        String formname = getName();
        
        clear();
        
        for (DocumentModelItem item : model.getItens()) {
            name = item.getName();
            formitem = new DataFormItem(this, Const.TEXT_FIELD,
                    new StringBuffer(formname).append(".").append(name).
                    toString());
            formitem.setModelItem(item);
        }
    }
}
