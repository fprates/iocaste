package org.iocaste.gateway;

import java.net.ServerSocket;
import java.net.Socket;

import org.iocaste.external.common.AbstractExternalApplication;
import org.iocaste.protocol.Message;

public class Main extends AbstractExternalApplication {

    @Override
    protected void config() {
        option("--gw-port", KEY_VALUE, "60080");
    }

    @Override
    protected void execute(Message message) throws Exception {
        ServerSocket localsocket;
        Socket remotesocket;
        int port = Integer.parseInt(message.getst("--gw-port"));
        
        localsocket = new ServerSocket(port);
        try {
            while (true) {
                remotesocket = localsocket.accept();
                new Dispatcher(remotesocket, connector).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            localsocket.close();
        }
    }
    
    public static final void main(String[] args) throws Exception {
        new Main().init(args);
    }
}