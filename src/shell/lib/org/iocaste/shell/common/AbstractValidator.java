package org.iocaste.shell.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.Function;

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
    private Map<String, InputComponent> inputs;
    private Function function;
    private String message, name;
    
    public AbstractValidator(AbstractContext context, String name) {
        ((AbstractPage)context.function).register("shitem", this);
        this.name = name;
        inputs = new HashMap<>();
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.Validator#add(
     *     org.iocaste.shell.common.InputComponent)
     */
    @Override
    public final void add(InputComponent input) {
        inputs.put(input.getName(), input);
    }
    
    /**
     * Disponibiliza function para a validação.
     * @return Function
     */
    protected final Function getFunction() {
        return function;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final InputComponent getInput(String name) {
        return inputs.get(name);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.Validator#getInputs()
     */
    @Override
    public final Collection<InputComponent> getInputs() {
        return inputs.values();
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
     * @see org.iocaste.shell.common.Validator#getName()
     */
    @Override
    public final String getName() {
        return name;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Validator#setFunction(
     *     org.iocaste.protocol.Function)
     */
    @Override
    public final void setFunction(Function function) {
        this.function = function;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.Validator#validate()
     */
    @Override
    public abstract void validate() throws Exception;

}
