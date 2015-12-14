package org.quantic.iocasteconnector;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.external.common.AbstractExternalApplication;
import org.iocaste.protocol.Message;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.ext.Environment;
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
        JCoDestination destination;
        RFCDataProvider provider;
        ComplexDocument config;
        Command stream;
        String text;
        ExtendedObject portdata;
        FunctionConfig function;
        List<FunctionConfig> functions;
        
        stream = new Command();
        stream.port = message.getString("--port");
        stream.locale = message.getString("--language");
        listennerport = Integer.parseInt(message.getString("--listenner-port"));
        
        System.out.print("getting connection data from iocaste...");
        config = external.getConnectionData(stream.port);
        external.disconnect();
        
        if (config == null) {
            System.err.println("connection data not found.");
            return;
        }
        
        System.out.println("ok");
        
        text = config.getHeader().getst("TEXT");
        System.out.println("* Connecting to " + text);

        registerable = false;
        functions = new ArrayList<>();
        for (ExtendedObject object : config.getItems("functions")) {
            function = new FunctionConfig();
            functions.add(function);
            function.name = object.getst("FUNCTION");
            function.service = object.getst("SERVICE");
            if (function.service == null)
                continue;
            
            function.handler = new FunctionHandler(connector, external, object);
            registerable = true;
        }
        
        portdata = config.getHeader();
        
        System.out.print("registering sap data provider...");
        
        provider = new RFCDataProvider();
        provider.setConfig(portdata, stream.locale);
        
        Environment.registerDestinationDataProvider(provider);
        Environment.registerServerDataProvider(provider);
        System.out.println("ok");

        System.out.print("bringing up iocaste listenners...");
        destination = JCoDestinationManager.
                getDestination(portdata.getst("PORT_NAME"));
        addListenner(
                listennerport, () -> new IocasteListenner(destination, config));
        System.out.println("ok");
        
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