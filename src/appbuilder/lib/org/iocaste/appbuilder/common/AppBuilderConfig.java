package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

public class AppBuilderConfig {
    private Map<String, AppBuilderFieldConfig> fields;
    
    public AppBuilderConfig() {
        fields = new HashMap<>();
    }
    
    public final AppBuilderFieldConfig fieldInstance(String field) {
        AppBuilderFieldConfig config = new AppBuilderFieldConfig();
        fields.put(field, config);
        return config;
    }
}
