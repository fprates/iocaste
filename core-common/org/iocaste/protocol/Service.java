package org.iocaste.protocol;

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
        this.sessionid = sessionid;
        this.urlname = urlname;
    }
    
    public final void setInputStream(InputStream is) {
        this.is = is;
    }
    
    public final void setOutputStream(OutputStream os) {
        this.os = os;
    }
    
    public final Message getMessage() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(is);
        Message message = (Message)ois.readObject();
        
        ois.close();
        
        return message;
    }
    
    public static final Object callServer(String urlname, Message message)
            throws Exception {
        Message response;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        URL url = new URL(urlname);
        URLConnection urlcon = url.openConnection();
        
        urlcon.setDoInput(true);
        urlcon.setDoOutput(true);
        
        oos = new ObjectOutputStream(urlcon.getOutputStream());
        oos.writeObject(message);
        oos.close();
        
        ois = new ObjectInputStream(urlcon.getInputStream());
        response = (Message)ois.readObject();
        ois.close();
        
        if (response.getException() != null)
            throw new Exception(response.getException());
        
        return response.get("return");
    }
    
    public final Object call(Message message) throws Exception {
        message.setSessionid(sessionid);
        
        return callServer(urlname, message);
    }
    
    private final void messageSend(
            Message message, Object object, Exception ex) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        
        message.clear();
        message.setId(null);
        message.add("return", object);
        message.setException(ex);
        
        oos.writeObject(message);
        oos.close();
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
