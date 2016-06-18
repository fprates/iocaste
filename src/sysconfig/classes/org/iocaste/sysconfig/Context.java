package org.iocaste.sysconfig;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class Context extends AbstractExtendedContext {
    public Map<String, ModuleConfig> modules;
    public Context(PageBuilderContext context) {
        super(context);
        modules = new LinkedHashMap<>();
        modules.put("shell", new ModuleConfig("SHELL_PROPERTIES", "VALUE"));
    }
    
}

class ModuleConfig {
    public String model;
    public String[] enabled;
    
    public ModuleConfig(String model, String... enabled) {
        this.model = model;
        this.enabled = enabled;
    }
}