package org.iocaste.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Service {

    public abstract Object call(Message message);
    
    public abstract Message getMessage() throws Exception;
    
    public abstract void messageException(
            Message message, Exception ex) throws IOException;
    
    public abstract void messageReturn(
            Message message, Object object) throws IOException;

    public abstract void setInputStream(InputStream is);
    
    public abstract void setOutputStream(OutputStream os);
}
