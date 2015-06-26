package org.iocaste.dataeditor;

import java.util.Map;

import org.iocaste.appbuilder.common.FieldProperty;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

public class GetFieldsProperties {

    public static final Map<String, FieldProperty> execute(
            PageBuilderContext context, Context extcontext) {
        String url;
        GenericService service;
        Message message;
        Map<String, FieldProperty> fields;
        
        if (extcontext.appname == null)
            return null;
        
        url = new StringBuilder("/").append(extcontext.appname).
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
}
