package org.iocaste.appbuilder.common;

import java.util.Map;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Appbuilder extends AbstractServiceInterface {
    private static final String SERVICE = "/iocaste-appbuilder/services.html";
    
    public Appbuilder(Function function) {
        initService(function, SERVICE);
    }
    
    public final Map<String, String> getProfileStyle() {
        return call(new Message("style_profile_get"));
    }
    
    public final Map<String, String> getStylesheet() {
        return call(new Message("stylesheet_get"));
    }
}
