package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

public class AppBuilderLink {
    private Map<String, AppBuilderConfig> configs;
    public String create, display, change, entity, cmodel, taskgroup;
    public String number, appname;
    
    public AppBuilderLink() {
        configs = new HashMap<>();
    }
    
    public final AppBuilderConfig configInstance(String link) {
        AppBuilderConfig config;
        
        config = new AppBuilderConfig();
        configs.put(link, config);
        return config;
    }
}
