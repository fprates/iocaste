package org.quantic.iocasteconnector;

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
        DefaultServerHandlerFactory.FunctionHandlerFactory factory;
        JCoServer server;
        JCoServerFunctionHandler handler;
        JCoDestination destination;
        RFCDataProvider provider;
        ComplexDocument config;
        Command stream;
        String name, text;
        ExtendedObject portdata;
        
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
        System.out.print("registering sap data provider...");
        provider = new RFCDataProvider();
        portdata = config.getHeader();
        provider.setConfig(portdata, stream.locale);
        
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
            text = object.getst("SERVICE");
            if (text == null) {
                System.out.println("- no handler for "+name+". skipping.");
                continue;
            }
            handler = new FunctionHandler(connector, external, object);
            factory.registerHandler(name, handler);
            System.out.println("- " + name + " registered.");
        }
        
        server.setCallHandlerFactory(factory);

        System.out.print("bringing up iocaste listenners...");
        destination = JCoDestinationManager.
                getDestination(portdata.getst("PORT_NAME"));
        addListenner(listennerport, () -> new IocasteListenner(destination));
        System.out.println("ok");
        
        System.out.println("listenning to connections...");
        server.start();
	}
	
	public static final void main(String[] args) throws Exception {
		new Main().init(args);
	}
}
