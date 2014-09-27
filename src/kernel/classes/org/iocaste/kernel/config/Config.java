package org.iocaste.kernel.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.iocaste.kernel.common.AbstractFunction;
import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.kernel.database.Database;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class Config extends AbstractFunction {
    private static final String IOCASTE_DIR = ".iocaste";
    private static final String CONFIG_FILE = "core.properties";
    public Properties properties;
    public String host;
    public Database database;
    
    public Config() {
        export("get_host", new GetHost(this));
        export("get_system_info", new GetSystemInfo());
        export("get_system_parameter", new GetSystemParameter(this));
    }
    
    /**
     * 
     */
    public final void init() throws Exception {
        BufferedReader reader;
        FileInputStream fis;
        String path = new StringBuilder(System.getProperty("user.home")).
                append(File.separator).append(IOCASTE_DIR).
                append(File.separator).append(CONFIG_FILE).toString();
        File file = new File(path);
        
        if (!file.exists() || !file.isFile())
            throw new IocasteException("Iocaste not configured. " +
                    "Contact the administrator.");
        
        fis = new FileInputStream(file);
        reader = new BufferedReader(new InputStreamReader(fis));
        properties = new Properties();
        properties.load(reader);
        reader.close();
        fis.close();
        
        database.config(properties);
    }
    
    /**
     * 
     * @return
     */
    public final boolean isInitialized() {
        return (properties != null);
    }
    
    /**
     * 
     * @param host
     */
    public final void setHost(String host) {
        this.host = host;
    }
}

class GetHost extends AbstractHandler {
    private Config config;
    
    public GetHost(Config config) {
        this.config = config;
    }
    
    @Override
    public Object run(Message message) throws Exception {
        return config.host;
    }
    
}

class GetSystemParameter extends AbstractHandler {
    private Config config;
    
    public GetSystemParameter(Config config) {
        this.config = config;
    }
    
    public final Object run(Message message) {
        String name = message.getString("parameter");
        return config.properties.getProperty(name);
    }
}