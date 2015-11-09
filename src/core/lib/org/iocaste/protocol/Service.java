package org.iocaste.protocol;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.iocaste.protocol.stream.ServiceStream;
import org.iocaste.protocol.stream.SocketStream;
import org.iocaste.protocol.stream.URLStream;

public class Service {
    private InputStream is;
    private OutputStream os;
    private ServiceStream stream;
    private String sessionid;
    
    public Service(String sessionid, String urlname) {
        this.sessionid = sessionid;
        stream = new URLStream(urlname);
    }
    
    public Service(String address, int port) {
        stream = new SocketStream(address, port);
    }
    
    public Service(ServiceStream stream) {
        this.stream = stream;
    }
    
    public final InputStream getInpustStream() {
        return is;
    }
    
    public final OutputStream getOutputStream() {
        return os;
    }
    
    public final void setInputStream(InputStream is) {
        this.is = is;
    }
    
    public final void setOutputStream(OutputStream os) {
        this.os = os;
    }
    
    public final Message getMessage() throws IOException,
            ClassNotFoundException {
        BufferedInputStream bis = new BufferedInputStream(is);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Message message = (Message)ois.readObject();
        
        return message;
    }
    
    public final Object call(Message message) {
        Message response;
        Throwable cause;
        
        message.setSessionid(sessionid);
        
        try {
            stream.open();
            stream.write(message);
            response = stream.read();
            stream.close();
            
            if (response.getException() != null)
                throw response.getException();
            
            return response.get("return");
        } catch (Exception e) {
            cause = e.getCause();
            throw new RuntimeException((cause == null)? e : cause);
        }
    }
    
    public final void messageException(
            Message message, Exception ex) throws IOException {
        messageSend(message, null, ex);
    }
    
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
}
