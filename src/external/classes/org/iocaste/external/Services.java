package org.iocaste.external;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.external.common.MessageExtractor;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.stream.SocketStream;

public class Services extends AbstractFunction {
    public int counter;
    public Map<String, Map<String, String>> handlers;
    
    public Services() {
        export("test", new Test());
        export("connect", new Connect());
        export("connection_data_get", new GetConnectionData());
        export("disconnect", new Disconnect());
        export("structures_function_get", new GetFunctionStructures());
        export("server_register", new Register());
        export("handler_install", new Install());
        export("extern_call", new ExternCall());
        handlers = new HashMap<>();
    }
}

class Register extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String name = message.getString("name");
        Services function = getFunction();
        
        if (!function.handlers.containsKey(name))
            function.handlers.put(name, new HashMap<>());
        return null;
    }
    
}

class Install extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String server = message.getString("server");
        String name = message.getString("name");
        Services function = getFunction();
        
        function.handlers.get(server).put(name, null);
        return null;
    }
    
}

class ExternCall extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Service service;
        String address = message.getString("address");
        int port = message.geti("port");
        
        service = new Service(new ExternCallStream(address, port));
        return service.call(message.get("message"));
    }
    
}

class ExternCallStream extends SocketStream {

    public ExternCallStream(String address, int port) {
        super(address, port);
    }
    
    @Override
    public final Message read() throws Exception {
        BufferedReader br;
        InputStream cis;
        Message response;
        
        cis = getInputStream();
        br = new BufferedReader(new InputStreamReader(cis));
        response = new MessageExtractor().execute(br);
        br.close();
        return response;
    }
    
    @Override
    public final void write(Message message) throws Exception {
        BufferedWriter bw;
        OutputStream cos;
        
        cos = getOutputStream();
        bw = new BufferedWriter(new OutputStreamWriter(cos));
        bw.write(message.toString());
        bw.flush();
    }
}