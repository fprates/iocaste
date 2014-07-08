package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.Collection;

import org.iocaste.protocol.Function;

public interface Validator extends Serializable {

    public abstract void add(InputComponent input);
    
    public abstract Collection<InputComponent> getInputs();
    
    public abstract String getMessage();
    
    public abstract String getName();
    
    public abstract void setFunction(Function function);
    
    public abstract void validate() throws Exception;
}
