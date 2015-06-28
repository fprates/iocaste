package org.quantic.iocasteconnector;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.external.common.AbstractExternalApplication;
import org.iocaste.external.common.External;
import org.iocaste.external.common.IocasteConnector;
import org.iocaste.protocol.Message;

import com.sap.conn.jco.JCoException;
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
	}

	@Override
	protected void execute(Message message) {
        DefaultServerHandlerFactory.FunctionHandlerFactory factory;
        JCoServer server;
        JCoServerFunctionHandler handler;
        RFCDataProvider provider;
        External external;
        IocasteConnector connector;
        ComplexDocument config;
        Command stream;
        String name, text;
        
        stream = new Command();
        stream.port = message.getString("--port");
        
        System.out.print("getting connection data from iocaste...");
        config = external.getConnectionData(stream.port);
        if (config == null) {
            System.err.println("connection data not found.");
            return;
        }
        
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

        System.out.println("listenning to connections...");
        server.start();
	}
	
	public static final void main(String[] args) {
		new Main().init(args);
	}
}