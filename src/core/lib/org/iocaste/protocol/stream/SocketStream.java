package org.iocaste.protocol.stream;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketStream extends AbstractServiceStream {
    private Socket socket;
    private String address;
    private int port;
    
    public SocketStream(String address, int port) {
        this.address = address;
        this.port = port;
    }
    
    @Override
    public final void close() throws Exception {
        socket.close();
    }
    
    @Override
    protected InputStream getInputStream() throws Exception {
        return socket.getInputStream();
    }
    
    @Override
    protected OutputStream getOutputStream() throws Exception {
        return socket.getOutputStream();
    }

    @Override
    public void open() throws Exception {
        socket = new Socket(InetAddress.getByName(address), port);
    }
}
