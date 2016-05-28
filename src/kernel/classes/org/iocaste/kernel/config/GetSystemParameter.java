package org.iocaste.kernel.config;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetSystemParameter extends AbstractHandler {
    private Config config;
    
    public GetSystemParameter(Config config) {
        this.config = config;
    }

    @Override
    public final Object run(Message message) {
        String name = message.getst("parameter");
        return run(name);
    }
    
    public final String run(String name) {
        return config.properties.getProperty(name);
    }
}