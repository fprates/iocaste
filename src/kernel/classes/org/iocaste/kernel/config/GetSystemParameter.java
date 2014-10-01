package org.iocaste.kernel.config;

import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetSystemParameter extends AbstractHandler {
    private Config config;
    
    public GetSystemParameter(Config config) {
        this.config = config;
    }

    @Override
    public final Object run(Message message) {
        String name = message.getString("parameter");
        return run(name);
    }
    
    public final String run(String name) {
        return config.properties.getProperty(name);
    }
}