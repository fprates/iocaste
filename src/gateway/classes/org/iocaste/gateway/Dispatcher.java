package org.iocaste.gateway;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

import org.iocaste.external.common.IocasteConnector;
import org.iocaste.external.common.MessageExtractor;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

public class Dispatcher extends Thread {
    private Socket socket;
    private IocasteConnector connector;

    public Dispatcher(Socket socket, IocasteConnector connector) {
        this.socket = socket;
        this.connector = connector;
    }

    private final void close(Socket socket, Writer writer, Reader reader) {
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
            
            if (reader != null)
                reader.close();
            
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public final void run() {
        Message message;
        Object object;
        GenericService service;
        BufferedReader reader = null;
        Writer writer = null;
        MessageExtractor extractor;
        
        try {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            
            extractor = new MessageExtractor();
            message = extractor.execute(reader);
            object = extractor.getService();
            if (object == null)
                return;
            
            service = new GenericService(connector, (String)object);
            object = service.invoke(message);
            writer.write((object == null)? "" : object.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(socket, writer, reader);
        }
    }
}
