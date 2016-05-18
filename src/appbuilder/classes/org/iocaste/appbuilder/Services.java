package org.iocaste.appbuilder;

import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.StyleSheet;

public class Services extends AbstractFunction {
    public Map<String, Map<String, String>> style;
    
    public Services() {
        Map<String, String> style;
        StyleSheet stylesheet = new StyleSheet();

        style = stylesheet.newElement(".content_area");
        style.put("max-width", "1400px");
        style.put("margin", "auto");
        
        this.style = stylesheet.getElements();
        export("stylesheet_get", new GetStyleSheet());
    }
}

class GetStyleSheet extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Services services = getFunction();
        return services.style;
    }
    
}