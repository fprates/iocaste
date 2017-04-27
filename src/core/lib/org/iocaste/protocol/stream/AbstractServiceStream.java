package org.iocaste.protocol.stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public abstract class AbstractServiceStream implements ServiceStream {

    @Override
    public abstract void close() throws Exception;

    protected abstract InputStream getInputStream() throws Exception;
    
    protected abstract OutputStream getOutputStream() throws Exception;
    
    @Override
    public abstract void open() throws Exception;

    @Override
    public Object[] read() throws Exception {
        ObjectInputStream ois;
        InputStream cis;
        Object[] response;
        
        cis = getInputStream();
        ois = new ObjectInputStream(new BufferedInputStream(cis));
        response = (Object[])ois.readObject();
        ois.close();
        return response;
    }

    @Override
    public void write(Object[] content) throws Exception {
        ObjectOutputStream oos;
        OutputStream cos;
        
        cos = getOutputStream();
        oos = new ObjectOutputStream(new BufferedOutputStream(cos));
        oos.writeObject(content);
        oos.flush();
    }

}
