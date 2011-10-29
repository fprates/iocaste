package org.iocaste.shell.common;

import java.lang.reflect.Method;

import org.iocaste.documents.common.DocumentModelItem;

public class Shell {

    /**
     * 
     * @param inputitem
     * @return
     */
    public static final Element createInputItem(Container container,
            AbstractInputComponent inputitem, String name) {
        TextField tfield;
        
        switch (inputitem.getComponentType()) {
        case TEXT_FIELD:
            tfield = new TextField(container, name);
            tfield.setStyleClass(inputitem.getStyleClass());
            tfield.setObligatory(inputitem.isObligatory());
            tfield.setPassword(inputitem.isSecret());
            tfield.setLength(inputitem.getLength());
            tfield.setValue(inputitem.getValue());
            tfield.setModelItem(inputitem.getModelItem());
            
            return tfield;
        default:
            return null;
        }
    }
    
    /**
     * 
     * @param input
     * @param object
     */
    public final static void moveItemToInput(InputComponent input, Object object) {
        Method method;
        Object value;
        DocumentModelItem modelitem = input.getModelItem();
        
        try {
            method = object.getClass().getMethod(modelitem.getGetterName());
            value = method.invoke(object, new Object[] {});
            input.setValue((value == null)?"":value.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
