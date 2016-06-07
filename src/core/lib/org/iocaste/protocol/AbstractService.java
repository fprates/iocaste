package org.iocaste.protocol;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.iocaste.protocol.stream.ServiceStream;
import org.iocaste.protocol.stream.SocketStream;
import org.iocaste.protocol.stream.URLStream;

public abstract class AbstractService implements Service {
    private InputStream is;
    private OutputStream os;
    private ServiceStream stream;
    private String sessionid;
    
    public AbstractService(String sessionid, String urlname) {
        this(new URLStream(urlname));
        this.sessionid = sessionid;
    }
    
    public AbstractService(String address, int port) {
        this(new SocketStream(address, port));
    }
    
    public AbstractService(ServiceStream stream) {
        this.stream = stream;
    }
    
    @Override
    public final Object call(Message message) {
        Message response;
        Throwable cause;
        
        message.setSessionid(sessionid);
        
        try {
            stream.open();
            stream.write(message);
            response = stream.read();
            stream.close();
            
            /*
             * ajuste temporário enquanto ajustamos iocaste-external/gateway,
             */
            if (response == null)
                return null;
            if (response.getException() != null)
                throw response.getException();
            
            return response.get("return");
        } catch (Exception e) {
            cause = e.getCause();
            throw new RuntimeException((cause == null)? e : cause);
        }
    }
    
    protected final InputStream getInputStream() {
        return is;
    }
    
    @Override
    public abstract Message getMessage() throws Exception;
    
    protected final OutputStream getOutputStream() {
        return os;
    }
    
    @Override
    public final void messageException(
            Message message, Exception ex) throws IOException {
        messageSend(message, null, ex);
    }
    
    @Override
    public final void messageReturn(
            Message message, Object object) throws IOException {
        messageSend(message, object, null);
    }
    
    private final void messageSend(
            Message message, Object object, Exception ex) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(os);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        
        message.clear();
        message.add("return", object);
        message.setException(ex);
        
        oos.writeObject(message);
        oos.flush();
    }
    
    @Override
    public final void setInputStream(InputStream is) {
        this.is = is;
    }
    
    @Override
    public final void setOutputStream(OutputStream os) {
        this.os = os;
    }
}
