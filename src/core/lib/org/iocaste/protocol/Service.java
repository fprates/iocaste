package org.iocaste.protocol;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class Service {
    private InputStream is;
    private OutputStream os;
    private String sessionid;
    private String urlname;
    private int type, port;
    
    public Service(String sessionid, String urlname) {
        this.urlname = urlname;
        this.sessionid = sessionid;
        type = 0;
    }
    
    public Service(String urlname, int port) {
        this.urlname = urlname;
        this.port = port;
        type = 1;
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
        Socket socket;
        Message response;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        InputStream cis;
        OutputStream cos;
        Throwable cause;
        URL url;
        URLConnection urlcon;
        
        message.setSessionid(sessionid);
        
        try {
            switch (type) {
            case 0:
                socket = null;
                url = new URL(urlname);
                urlcon = url.openConnection();
                urlcon.setDoInput(true);
                urlcon.setDoOutput(true);
                cos = urlcon.getOutputStream();
                break;
            default:
                urlcon = null;
                socket = new Socket(InetAddress.getByName(urlname), port);
                cos = socket.getOutputStream();
                break;
            }
            
            oos = new ObjectOutputStream(new BufferedOutputStream(cos));
            oos.writeObject(message);
            oos.flush();
            
            switch (type) {
            case 0:
                cis = urlcon.getInputStream();
                break;
            default:
                cis = socket.getInputStream();
                break;
            }
            
            ois = new ObjectInputStream(new BufferedInputStream(cis));
            response = (Message)ois.readObject();
            ois.close();
            oos.close();
            
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
