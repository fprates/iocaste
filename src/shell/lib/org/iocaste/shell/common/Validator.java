package org.iocaste.shell.common;

public interface Validator {
    
    public abstract void clear();
    
    public abstract String getMessage();
    
    public abstract void setInput(InputComponent input);
    
    public abstract void validate(AbstractContext context) throws Exception;
}
