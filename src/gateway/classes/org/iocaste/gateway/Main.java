package org.iocaste.gateway;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.iocaste.external.common.AbstractExternalApplication;
import org.iocaste.protocol.Message;

public class Main extends AbstractExternalApplication {

    @Override
    protected void config() {
        option("--gw-port", KEY_VALUE, "70000");
    }

    @Override
    protected void execute(Message message) throws Exception {
        ServerSocket localsocket;
        Socket remotesocket;
        BufferedWriter writer;
        BufferedReader reader;
        String line;
        int port = Integer.parseInt(message.getString("--gw-port"));
        
        localsocket = new ServerSocket(port);
        remotesocket = localsocket.accept();
        writer = new BufferedWriter(
                new OutputStreamWriter(remotesocket.getOutputStream()));
        reader = new BufferedReader(
                new InputStreamReader(remotesocket.getInputStream()));
        while ((line = reader.readLine()) != null) {
            
        }
        localsocket.close();
    }

    public static final void main(String[] args) throws Exception {
        new Main().init(args);
    }
}
