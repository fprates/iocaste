package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

public class GetFieldsProperties extends AbstractHandler {
    private Map<String, Map<String, FieldProperty>> fields;
    
    public GetFieldsProperties() {
        fields = new HashMap<>();
    }

    public static final Map<String, FieldProperty> execute(
            PageBuilderContext context, String appname) {
        String url;
        GenericService service;
        Message message;
        Map<String, FieldProperty> fields;
        
        if (appname == null)
            return null;
        
        url = new StringBuilder("/").append(appname).
                append("/view.html").toString();
        
        message = new Message("fields_properties_get");
        message.add("page", "main");
        service = new GenericService(context.function, url);
        try {
            fields = service.invoke(message);
            return fields;
        } catch (Exception e) {
            return null;
        }
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
    
    @Override
    public Object run(Message message) throws Exception {
        AbstractPageBuilder function = getFunction();
        String page = message.getst("page");
        
        if (!fields.containsKey(page))
            function.config(this);
        
        return fields.get(page);
    }

}
