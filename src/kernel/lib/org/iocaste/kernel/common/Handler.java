package org.iocaste.kernel.common;

public interface Handler {

    public abstract Object run(Message message) throws Exception;
    
    public abstract void setFunction(Function function);
}
