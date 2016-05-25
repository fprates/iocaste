package org.iocaste.external.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.iocaste.protocol.Message;

public abstract class AbstractExternalApplication {
	protected static final int KEY = 0;
	protected static final int KEY_VALUE = 1;
	private static final int VALUE = 2;
	private static boolean OPTION = false;
	private static boolean REQUIRED = true;
	protected Map<String, ParameterEntry> parameters;
	protected IocasteConnector connector;
	protected External external;
	protected ApplicationDefaultConfig preconfig;
	private Map<Integer, Server> servers;
	
	public AbstractExternalApplication() {
		parameters = new HashMap<>();
		preconfig = new ApplicationDefaultConfig();
		servers = new HashMap<>();
		
		required("--host", KEY_VALUE);
		required("--user", KEY_VALUE);
		option("--password", KEY_VALUE);
		option("--language", KEY_VALUE, "pt_BR");
		option("--config-file", KEY_VALUE);
		config();
	}
	
	protected final void addListenner(
	        int port, ListennerFactory factory) {
	    Server server = new Server();
	    server.port = port;
	    server.connector = connector;
	    server.factory = factory;
	    server.external = external;
	    servers.put(port, server);
	    server.start();
	}
	
	protected abstract void config();
	
	private final void connect(Message message) {
		String host, user, secret, locale;
        char[] buffer;
		
		host = message.getString("--host");
		user = message.getString("--user");
		secret = message.getString("--password");
        locale = message.getString("--language");
        
        if (secret == null) {
            System.out.print("Password:");
            buffer = System.console().readPassword();
            if (buffer == null) {
                System.err.println("Password not specified.");
                System.exit(1);
            }
            
            secret = new String(buffer);
        }
        
        System.out.print("trying connection to iocaste...");
        host = "http://".concat(host);
        connector = new IocasteConnector(host);
        external = new External(connector);
        external.setConnection(user, secret, locale);
        if (!external.connect()) {
            System.err.println("Connection failed.");
            System.exit(1);
        }
        
        System.out.println("ok");
	}
	
	private final void entry(
			String name, int option, boolean required, String value) {
		ParameterEntry entry = new ParameterEntry();
		
		entry.option = option;
		entry.required = required;
		entry.value = value;
		parameters.put(name, entry);
	}
	
	protected abstract void execute(Message message) throws Exception;
	
	private final Message getParametersFromFile(String file) throws Exception {
        Properties properties;
        BufferedReader reader;
        Message message;
        
        reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file)));
        message = new Message("execute");
        properties = new Properties();
        properties.load(reader);
        
        for (Object key : properties.keySet()) {
            message.add(String.format("--%s", key),
                    properties.getProperty((String)key));
        }
        
        reader.close();
        return message;
	}
	
	public final void init(String[] args) throws Exception {
        Message message;
        int stage;
        String key, value, configfile;
        ParameterEntry entry;
        
        message = new Message("execute");
        
        stage = KEY;
        key = null;
        for (String arg : args) {
            switch(stage) {
            case KEY:
            	key = arg;
            	entry = parameters.get(key);
            	if (entry == null) {
            		System.err.println(arg.concat(" is an invalid argument."));
            		System.exit(1);
            	}
            	if (entry.option == KEY_VALUE)
            		stage = VALUE;
                continue;
            case VALUE:
            	message.add(key, arg);
            	stage = KEY;
                continue;
            }
        }
        
        configfile = message.getString("--config-file");
        if (configfile != null)
            message = getParametersFromFile(configfile);
        
        for (String name : parameters.keySet()) {
        	entry = parameters.get(name);
        	if (!entry.required) {
        		value = message.getString(name);
        		if ((entry.value != null) && (value == null))
        			message.add(name, entry.value);
        		continue;
        	}
        	if (message.get(name) != null)
        		continue;
        	System.err.println(name.concat(" required."));
        	System.exit(1);
        }
        
        if (!preconfig.noconnect)
        	connect(message);
        
        execute(message);
	}
	
	protected final void option(String name, int option) {
		option(name, option, null);
	}
	
	protected final void option(String name, int option, String value) {
		entry(name, option, OPTION, value);
	}
	
	protected final void required(String name, int option) {
		entry(name, option, REQUIRED, null);
	}
}

class ParameterEntry {
	public int option;
	public boolean required;
	public String value;
}

class Server extends Thread {
    public int port;
    public IocasteConnector connector;
    public External external;
    public ListennerFactory factory;
    
    @Override
    public final void run() {
        ServerSocket localsocket;
        Socket remotesocket;
        AbstractExternalFunction listenner = null;
        
        try {
            localsocket = new ServerSocket(port);
            try {
                while (true) {
                    remotesocket = localsocket.accept();
                    listenner = factory.instance();
                    listenner.setConnector(connector);
                    listenner.setExternal(external);
                    listenner.setSocket(remotesocket);
                    listenner.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                localsocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
