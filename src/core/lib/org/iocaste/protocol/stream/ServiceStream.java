package org.iocaste.protocol.stream;

public interface ServiceStream {
    
    public abstract void close() throws Exception;
    
    public abstract void open() throws Exception;
    
    public abstract Object[] read() throws Exception;
    
    public abstract void write(Object[] content) throws Exception;
}