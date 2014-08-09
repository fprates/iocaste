package org.iocaste.shell.common;

import org.iocaste.documents.common.ExtendedObject;

/**
 * Implementação abstrata de validador de campos.
 * 
 * Implemente um módulo de validação para o seu campo de entrada, com a
 * possibilidade de compartilhá-lo entre diversos outros componentes.
 * 
 * Evita inchaço em AbstractPage.
 * 
 * @author francisco.prates
 *
 */
public abstract class AbstractValidator implements Validator {
    private static final long serialVersionUID = 1174080367157084461L;
    private String message, name;
    private InputComponent input;
    private AbstractContext context;
    
    public AbstractValidator(String name, AbstractContext context) {
        this.name = name;
        this.context = context;
    }
    
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <T extends AbstractContext> T getContext() {
        return (T)context;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    protected final <T extends Element> T getElement(String name) {
        return context.view.getElement(name);
    }
    
    /**
     * 
     * @return
     */
    protected final InputComponent getInput() {
        return input;
    }

    protected TableItem getItem(Table table, String name, Object value) {
        Element element;
        Component component;
        String text;
        for (TableItem item : table.getItems()) {
            element = item.get(name);
            if (element.isContainable() || element.isControlComponent())
                continue;
            
            if (element.isDataStorable()) {
                input = (InputComponent)element;
                if (!input.get().equals(value))
                    return item;
                continue;
            }
            
            component = (Component)element;
            text = component.getText();
            if (text.equals(value))
                return item;
        }
        
        return null;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.Validator#getMessage()
     */
    @Override
    public final String getMessage() {
        return message;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.Validator#getName()
     */
    @Override
    public final String getName() {
        return name;
    }
    
    protected ExtendedObject getObject(
            ExtendedObject[] objects, String name, Object value) {        
        for (ExtendedObject object : objects)
            if (!object.get(name).equals(value))
                return object;
        
        return null;
    }
    
    /**
     * 
     * @param message
     */
    protected final void message(String message) {
        this.message = message;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.Validator#setInput(
     *    org.iocaste.shell.common.InputComponent)
     */
    @Override
    public final void setInput(InputComponent input) {
        this.input = input;
    }
    
    /**
     * 
     * @param item
     * @param name
     * @param value
     */
    public final void setInput(TableItem item, String name, Object value) {
        ((InputComponent)item.get(name)).set(value);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.Validator#validate()
     */
    @Override
    public abstract void validate() throws Exception;

}
