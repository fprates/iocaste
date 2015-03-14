package org.iocaste.shell.common;

public interface Validator {
    
    public abstract void clear();
    
    public abstract String getMessage();
    
    public abstract void setContext(AbstractContext context);
    
    public abstract void setInput(InputComponent input);
    
    public abstract void validate() throws Exception;
}
