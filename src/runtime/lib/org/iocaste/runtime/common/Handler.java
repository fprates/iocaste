package org.iocaste.runtime.common;

import org.iocaste.protocol.Message;

public interface Handler {

    public abstract Object run(Message message) throws Exception;
    
    public abstract void setFunction(Function function);
}
