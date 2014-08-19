package org.iocaste.protocol;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Service {
    private InputStream is;
    private OutputStream os;
    private String sessionid;
    private String urlname;
    
    public Service(String sessionid, String urlname) {
        this.urlname = urlname;
        this.sessionid = sessionid;
    }
    
    public final void setInputStream(InputStream is) {
        this.is = is;
    }
    
    public final void setOutputStream(OutputStream os) {
        this.os = os;
    }
    
    public final Message getMessage() throws IOException,
            ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(is);
        Message message = (Message)ois.readObject();
        
        return message;
    }
    
    public final Object call(Message message) {
        Message response;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        
        message.setSessionid(sessionid);
        
        try {
            URL url = new URL(urlname);
            URLConnection urlcon = url.openConnection();
            
            urlcon.setDoInput(true);
            urlcon.setDoOutput(true);
            
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(urlcon.getOutputStream()));
            oos.writeObject(message);
            oos.flush();
            
            ois = new ObjectInputStream(
                    new BufferedInputStream(urlcon.getInputStream()));
            response = (Message)ois.readObject();
            ois.close();
            oos.close();
            
            if (response.getException() != null)
                throw response.getException();
            
            return response.get("return");
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }
    
    private final void messageSend(
            Message message, Object object, Exception ex) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        
        message.clear();
        message.add("return", object);
        message.setException(ex);
        
        oos.writeObject(message);
        oos.flush();
    }
    
    public final void messageReturn(
            Message message, Object object) throws IOException {
        messageSend(message, object, null);
    }
    
    public final void messageException(
            Message message, Exception ex) throws IOException {
        messageSend(message, null, ex);
    }
}
