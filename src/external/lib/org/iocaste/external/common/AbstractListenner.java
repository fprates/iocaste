package org.iocaste.external.common;

import java.net.Socket;

public abstract class AbstractListenner extends Thread {
    private Socket socket;
    private IocasteConnector connector;
    
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
    
    public final void setSocket(Socket socket) {
        this.socket = socket;
    }
}
