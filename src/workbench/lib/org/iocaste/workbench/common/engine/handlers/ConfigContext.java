package org.iocaste.workbench.common.engine.handlers;

import java.util.HashMap;
import java.util.Map;

public class ConfigContext {
    public Map<String, ElementConfigHandler> handlers;
    
    public ConfigContext() {
        handlers = new HashMap<>();
    }
}
