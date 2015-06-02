package org.quantic.iocasteconnector;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.external.common.External;
import org.iocaste.external.common.IocasteConnector;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.Environment;
import com.sap.conn.jco.server.DefaultServerHandlerFactory;
import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerFactory;
import com.sap.conn.jco.server.JCoServerFunctionHandler;

public class Main {
    
    public static final void main(String[] args) throws JCoException  {
        DefaultServerHandlerFactory.FunctionHandlerFactory factory;
        JCoServer server;
        JCoServerFunctionHandler handler;
        RFCDataProvider provider;
        External external;
        IocasteConnector connector;
        ComplexDocument config;
        Map<String, Command.Parameters> parameters;
        Command.Parameters waitfor;
        Command stream;
        String name, text;
        char[] buffer;
        
        stream = new Command();
        parameters = new HashMap<>();
        parameters.put("--user", Command.Parameters.USER);
        parameters.put("--host", Command.Parameters.HOST);
        parameters.put("--port", Command.Parameters.PORT);
        parameters.put("--secret", Command.Parameters.SECRET);
        
        waitfor = stream.type = Command.Parameters.NEXT;
        for (String arg : args) {
            switch(waitfor) {
            case SECRET:
                stream.secret = arg;
                waitfor = Command.Parameters.NEXT;
                continue;
            case HOST:
                stream.host = arg;
                waitfor = Command.Parameters.NEXT;
                continue;
            case USER:
                stream.user = arg;
                waitfor = Command.Parameters.NEXT;
                continue;
            case LOCALE:
                stream.locale = arg;
                waitfor = Command.Parameters.NEXT;
                continue;
            case PORT:
                stream.port = arg;
                waitfor = Command.Parameters.NEXT;
                break;
            default:
                break;
            }

            waitfor = parameters.get(arg);
            if (waitfor == null) {
                waitfor = Command.Parameters.NEXT;
                continue;
            }
        }
        
        if (stream.host == null) {
            System.err.println("Host not specified.");
            return;
        }
        
        if (stream.user == null) {
            System.err.println("Username not specified.");
            return;
        }
        
        if (stream.port == null) {
            System.err.println("Connection port not specified.");
            return;
        }
        
        if (stream.locale == null)
            stream.locale = "pt_BR";
        
        if (stream.secret == null) {
            System.out.print("Password:");
            buffer = System.console().readPassword();
            if (buffer == null) {
                System.err.println("Password not specified.");
                return;
            }
            stream.secret = new String(buffer);
        }
        
        System.out.print("trying connection to iocaste...");
        stream.host = "http://".concat(stream.host);
        connector = new IocasteConnector(stream.host);
        external = new External(connector);
        if (!external.connect(stream.user, stream.secret, stream.locale)) {
            System.err.println("Connection failed.");
            return;
        }
        System.out.println("ok");
        
        System.out.print("getting connection data from iocaste...");
        config = external.getConnectionData(stream.port);
        if (config == null)
            System.err.println("connection data not found.");
        System.out.println("ok");

        text = config.getHeader().getst("TEXT");
        System.out.println("* Connecting to " + text);
        System.out.print("registering sap data provider...");
        provider = new RFCDataProvider();
        provider.setConfig(config.getHeader(), stream.locale);
        
        Environment.registerDestinationDataProvider(provider);
        Environment.registerServerDataProvider(provider);
        System.out.println("ok");
        
        System.out.print("trying registering on SAP...");
        server = JCoServerFactory.getServer(stream.port);
        System.out.println("ok");
        
        System.out.println("registering RFCs...");
        factory = new DefaultServerHandlerFactory.FunctionHandlerFactory();
        for (ExtendedObject object : config.getItems("functions")) {
            name = object.getst("FUNCTION");
            handler = new FunctionHandler(connector, external, object);
            factory.registerHandler(name, handler);
            System.out.println("- " + name + " registered.");
        }
        
        server.setCallHandlerFactory(factory);

        System.out.println("listenning connections...");
        server.start();
    }
}