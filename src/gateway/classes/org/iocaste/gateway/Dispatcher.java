package org.iocaste.gateway;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.iocaste.external.common.IocasteConnector;
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
    
    private Message extract(Socket socket, Properties properties) {
        Map<String, MessageParameter> values;
        String name, value;
        MessageParameter parameter;
        Message message;
        int type = 0;
        
        name = properties.getProperty("function");
        if (name == null)
            return null;
        
        message = new Message(name);
        message.setSessionid(properties.getProperty("sessionid"));
        
        values = new HashMap<>();
        for (Object key : properties.keySet()) {
            name = (String)key;
            value = (String)properties.getProperty(name);
            if (name.startsWith("type.")) {
                name = name.substring(5);
                type = Integer.parseInt(value);
                setType(values, name, type);
                continue;
            }
            
            if (name.startsWith("name.")) {
                name = name.substring(5);
                setValue(values, name, value);
            }
        }
        
        for (String key : values.keySet()) {
            parameter = values.get(key);
            switch (parameter.type) {
            case 5:
                message.add(key, parameter.value);
                break;
            case 6:
            case 7:
                message.add(key, Integer.parseInt(parameter.value));
                break;
            }
        }

        return message;
    }
    
    private Properties getProperties(BufferedReader reader) throws Exception {
        String line;
        String[] tokens;
        Properties properties;
        
        properties = new Properties();
        while ((line = reader.readLine()) != null) {
            if (line.equals("EOM"))
                break;
            tokens = line.split("[=]", 2);
            if ((tokens == null) || tokens.length < 2)
                continue;
            properties.put(tokens[0].trim(), tokens[1].trim());
        }
        
        return properties;
    }
    
    private MessageParameter instance(
            Map<String, MessageParameter> values, String name) {
        MessageParameter parameter = values.get(name);
        
        if (parameter == null) {
            parameter = new MessageParameter();
            values.put(name, parameter);
        }
        return parameter;
    }
    
    @Override
    public final void run() {
        Message message;
        Object object;
        GenericService service;
        Properties properties;
        BufferedReader reader = null;
        Writer writer = null;
        
        try {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            
            properties = getProperties(reader);
            message = extract(socket, properties);
            object = properties.getProperty("service");
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
    
    private final void setType(
            Map<String, MessageParameter> values, String name, int type) {
        instance(values, name).type = type;
    }
    
    private final void setValue(
            Map<String, MessageParameter> values, String name, String value) {
        instance(values, name).value = value;
    }
}

class MessageParameter {
    public int type;
    public String value;
}
