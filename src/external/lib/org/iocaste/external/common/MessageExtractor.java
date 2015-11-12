package org.iocaste.external.common;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.iocaste.protocol.Message;

public class MessageExtractor {
    private Properties properties;
    
    private Properties getProperties(BufferedReader reader)
            throws Exception {
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
    
    public Message execute(BufferedReader reader) throws Exception {
        Map<String, MessageParameter> values;
        String name, value;
        MessageParameter parameter;
        Message message;
        int type = 0;
        
        properties = getProperties(reader);
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
    
    public final String getService() {
        return properties.getProperty("service");
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
