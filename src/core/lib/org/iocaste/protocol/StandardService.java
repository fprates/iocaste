package org.iocaste.protocol;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.iocaste.protocol.stream.ServiceStream;
import org.iocaste.protocol.stream.SocketStream;

public class StandardService extends AbstractService {

    public StandardService(ServiceStream stream) {
        super(stream);
    }
    
    public StandardService(String sessionid, String url) {
        super(sessionid, url);
    }
    
    public StandardService(String address, int port) {
        super(new SocketStream(address, port));
    }
    
    @Override
    public final Message getMessage() throws IOException,
            ClassNotFoundException {
        BufferedInputStream bis = new BufferedInputStream(getInputStream());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object[] content = (Object[])ois.readObject();
        
        return assembly(content);
    }
}
