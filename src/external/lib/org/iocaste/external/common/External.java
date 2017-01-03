package org.iocaste.external.common;

import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

public class External extends AbstractServiceInterface {
    private static final String SERVICE = "/iocaste-external/services.html";
    private Function function;
    private String user, secret, locale;
    
    public External(Function function) {
        this.function = function;
        initService(function, SERVICE);
    }
    
    public final <T> T call(String address, int port, Message message) {
        Message extmessage = new Message("extern_call");
        extmessage.add("message", message);
        extmessage.add("address", address);
        extmessage.add("port", port);
        return call(extmessage);
    }
    
    public final boolean connect() {
        Message message;
        String sessionid;
        
        message = new Message("connect");
        message.add("user", user);
        message.add("secret", secret);
        message.add("locale", locale);
        sessionid = call(message);
        if (sessionid == null)
            return false;
        
        function.setSessionid(sessionid);
        initService(function, SERVICE);
        return true;
    }
    
    public final Function connector() {
        return function;
    }
    
    public static final External connectionInstance(
            Function function, String port, String language) {
        Function connector;
        External external;
        String user, secret, hostname;
        ExtendedObject object;
        ComplexDocument connection;
        
        external = new External(function);
        connection = external.getConnectionData(port);
        if (connection == null)
            throw new RuntimeException("remote connection config undefined.");
        
        object = connection.getHeader();
        user = object.getst("USERNAME");
        secret = object.getst("SECRET");
        hostname = object.getst("HOST");
        
        connector = new IocasteConnector(hostname);
        external = new External(connector);
        external.setConnection(user, secret, language);
        if (external.connect())
            return external;
        throw new RuntimeException("error connecting to remote iocaste.");
    }
    
    public final Function dbInstance(String dbname) {
        Function function;
        Message message;
        String sessionid;
        
        message = new Message("ext_db_instance");
        message.add("dbname", dbname);
        sessionid = call(message);
        
        function = new ExternalFunction();
        function.setSessionid(sessionid);
        return function;
    }
    
    public final void disconnect() {
        call(new Message("disconnect"));
    }
    
    public final ComplexDocument getConnectionData(String name) {
        Message message;
        
        message = new Message("connection_data_get");
        message.add("name", name);
        return call(message);
    }
    
    public final Map<String, ComplexDocument> getFunctionStructures(
            String function) {
        Message message;
        
        message = new Message("structures_function_get");
        message.add("function", function);
        return call(message);
    }
    
    public final GenericService serviceInstance(String url) {
        return new GenericService(function, url);
    }
    
    public final void setConnection(String user, String secret, String locale) {
        this.user = user;
        this.secret= secret;
        this.locale = locale;
    }
}

class ExternalFunction extends AbstractFunction {
    
}
