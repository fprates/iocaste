package org.quantic.iocasteconnector;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.external.common.AbstractExternalApplication;
import org.iocaste.protocol.Message;

import com.sap.conn.jco.server.DefaultServerHandlerFactory;
import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerFactory;
import com.sap.conn.jco.server.JCoServerFunctionHandler;

public class Main extends AbstractExternalApplication {

	@Override
	protected void config() {
		required("--port", KEY_VALUE);
		option("--language", KEY_VALUE, "pt_BR");
		option("--listenner-port", KEY_VALUE, "60000");
	}

	@Override
	protected void execute(Message message) throws Exception {
        int listennerport;
        boolean registerable;
        DefaultServerHandlerFactory.FunctionHandlerFactory factory;
        JCoServer server;
        Command stream;
        String text;
        FunctionConfig function;
        List<FunctionConfig> functions;
        
        stream = new Command();
        stream.port = message.getString("--port");
        stream.locale = message.getString("--language");
        listennerport = Integer.parseInt(message.getString("--listenner-port"));
        
        System.out.print("getting connection data from iocaste...");

        stream.portconfig = external.getConnectionData(stream.port);
        external.disconnect();
        
        if (stream.portconfig == null) {
            System.err.println("connection data not found.");
            return;
        }
        
        System.out.println("ok");
        
        text = stream.portconfig.getHeader().getst("TEXT");
        System.out.println("* Connecting to " + text);

        registerable = false;
        functions = new ArrayList<>();
        for (ExtendedObject object : stream.portconfig.getItems("functions")) {
            function = new FunctionConfig();
            functions.add(function);
            function.name = object.getst("FUNCTION");
            function.service = object.getst("SERVICE");
            if (function.service == null)
                continue;
            
            function.handler = new FunctionHandler(connector, external, object);
            registerable = true;
        }
        
        System.out.print("registering sap data provider...");
        AbstractSAPFunctionHandler.register(stream);
        System.out.println("ok");
        
        addListenner(listennerport, () -> new IocasteListenner(stream));
        
        if (registerable) {
            
            System.out.print("trying registering on SAP...");
            server = JCoServerFactory.getServer(stream.port);
            System.out.println("ok");
            
            System.out.println("registering RFCs...");
            factory = new DefaultServerHandlerFactory.FunctionHandlerFactory();
            for (FunctionConfig fc : functions) {
                if (fc.service == null) {
                    System.out.println(new StringBuilder("- no handler for ").
                            append(fc.name).
                            append(". skipping.").toString());
                    continue;
                }
                factory.registerHandler(fc.name, fc.handler);
                System.out.println("- " + fc.name + " registered.");
            }
            server.setCallHandlerFactory(factory);
            
            System.out.println("listenning to connections...");
            server.start();
        } else {
            System.out.println("listenning to connections...");
        }
	}
	
	public static final void main(String[] args) throws Exception {
		new Main().init(args);
	}
}

class FunctionConfig {
    String name, service;
    JCoServerFunctionHandler handler;
}