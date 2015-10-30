package org.iocaste.external.common;

import java.net.Socket;

public abstract class AbstractExternalFunction extends Thread {
    private Socket socket;
    private IocasteConnector connector;
    protected External external;
    
    @Override
    public final void run() {
        try {
            execute(socket, connector);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public abstract void execute(Socket socket, IocasteConnector connector)
            throws Exception;
    
    public final void setConnector(IocasteConnector connector) {
        this.connector = connector;
    }
    
    public final void setExternal(External external) {
        this.external = external;
    }
    
    public final void setSocket(Socket socket) {
        this.socket = socket;
    }
}
