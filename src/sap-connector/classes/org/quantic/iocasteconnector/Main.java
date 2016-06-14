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
    private JCoServer server;
    private static Main instance;
    
    @Override
    protected final void config() {
        required("--port", KEY_VALUE);
        option("--language", KEY_VALUE, "pt_BR");
        option("--listenner-port", KEY_VALUE, "60000");
        option("--net-debug", KEY);
    }
    
    @Override
    protected final void connect(Message message) throws Exception {
        if (message.getbl("--net-debug"))
            System.setProperty("javax.net.debug", "all");
        if (message.getst("--cert-alias") != null)
            CertificateInstall.run(
                    message.getst("--cert-alias"),
                    message.getst("--connector-path"),
                    message.getst("--cert-name"),
                    message.getst("--cert-secret").toCharArray());
        super.connect(message);
    }
    
	@Override
	protected void execute(Message message) throws Exception {
        int listennerport;
        DefaultServerHandlerFactory.FunctionHandlerFactory factory;
        Command stream;
        String text, name, service;
        List<FunctionConfig> functions;
        
        stream = new Command();
        stream.port = message.getst("--port");
        stream.locale = message.getst("--language");
        listennerport = Integer.parseInt(message.getst("--listenner-port"));
        
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

        functions = new ArrayList<>();
        for (ExtendedObject object : stream.portconfig.getItems("functions")) {
            name = object.getst("FUNCTION");
            service = object.getst("SERVICE");
            handlerRegister(functions, name,
                    service, new FunctionHandler(connector, external, object));
        }
        
        System.out.print("registering sap data provider...");
        AbstractSAPFunctionHandler.register(stream);
        System.out.println("ok");
        
        addListenner(listennerport, () -> new IocasteListenner(stream));
        
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
	}

    private void handlerRegister(List<FunctionConfig> functions, String name,
            String service, JCoServerFunctionHandler handler) {
        FunctionConfig function;
        
        function = new FunctionConfig();
        functions.add(function);
        function.name = name;
        function.service = service;
        if (function.service != null)
            function.handler = handler;
    }
	
	public static final void main(String[] args) {
		start(args);
	}
	
	public static final void start(String[] args) {
	    try {
    	    instance = new Main();
    	    instance.init(args);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public final void stop() {
	    server.stop();
	    external.disconnect();
        System.out.println("finishing connections...");
	}
	
	public static final void stop(String[] args) {
	    instance.stop();
	}
}

class FunctionConfig {
    String name, service;
    JCoServerFunctionHandler handler;
}