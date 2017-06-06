package org.iocaste.protocol;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.iocaste.protocol.stream.ServiceStream;
import org.iocaste.protocol.stream.SocketStream;
import org.iocaste.protocol.stream.URLStream;
import org.iocaste.protocol.utils.Tools;

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
    
    public static final Message assembly(Object[] content) {
        Object[][] values = (Object[][])content[3];
        Message message = new Message((String)content[0]);
        message.setSessionid((String)content[1]);
        message.setException((Exception)content[2]);
        for (int i = 0; i < values.length; i++)
            message.add((String)values[i][0], values[i][1]);
        return message;
    }
    
    @Override
    public final Object call(Message message) {
        Message response;
        Throwable cause;
        Object[] content;
        
        if (sessionid != null)
            message.setSessionid(sessionid);
        content = disassembly(message);
        try {
            stream.open();
            stream.write(content);
            content = stream.read();
            stream.close();
            
            /*
             * ajuste temporário enquanto ajustamos iocaste-external/gateway,
             */
            if (content == null)
                return null;
            response = assembly(content);
            if (response.getException() != null)
                throw response.getException();
            
            return response.get("return");
        } catch (Exception e) {
            cause = e.getCause();
            throw new RuntimeException((cause == null)? e : cause);
        }
    }
    
    public static final Object[] disassembly(Message message) {
        return new Object[] {
                message.getId(),
                message.getSessionid(),
                message.getException(),
                Tools.toArray(message.getParameters())
        };
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
        
        oos.writeObject(disassembly(message));
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
