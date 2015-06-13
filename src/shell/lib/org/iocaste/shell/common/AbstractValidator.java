package org.iocaste.shell.common;

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
    private String message;
    private InputComponent input;
    private AbstractContext context;
    
    @Override
    public final void clear() {
        message = null;
    }
    
    protected final boolean isItemElementMatch(Element element, Object value) {
        InputComponent input;
        Component component;
        String text;
        
        if (element.isContainable() || element.isControlComponent())
            return false;
        
        if (element.isDataStorable()) {
            input = (InputComponent)element;
            if (Shell.areEquals(input, value))
                return true;
            return false;
        }
        
        component = (Component)element;
        text = component.getText();
        return text.equals(value);
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
        
        for (TableItem item : table.getItems()) {
            element = item.get(name);
            if (!isItemElementMatch(element, value))
                continue;
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
    
    /**
     * 
     * @param message
     */
    protected final void message(String message) {
        this.message = message;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.Validator#setContext(
     *    org.iocaste.shell.common.AbstractContext)
     */
    @Override
    public final void setContext(AbstractContext context) {
        this.context = context;
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
