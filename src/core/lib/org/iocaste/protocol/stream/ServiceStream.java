package org.iocaste.protocol.stream;

import org.iocaste.protocol.Message;

public interface ServiceStream {
    
    public abstract void close() throws Exception;
    
    public abstract void open() throws Exception;
    
    public abstract Message read() throws Exception;
    
    public abstract void write(Message message) throws Exception;
}