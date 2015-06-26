package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetFieldsProperties extends AbstractHandler {
    private Map<String, Map<String, FieldProperty>> fields;
    
    public GetFieldsProperties() {
        fields = new HashMap<>();
    }
    
    @Override
    public Object run(Message message) throws Exception {
        AbstractPageBuilder function = getFunction();
        String page = message.getString("page");
        
        if (!fields.containsKey(page))
            function.config(this);
        
        return fields.get(page);
    }
    
    public final Map<String, FieldProperty> instance(String page) {
        Map<String, FieldProperty> properties;
        
        properties = fields.get(page);
        if (properties == null) {
            properties = new HashMap<>();
            fields.put(page, properties);
        }
        
        return properties;
    }

}
