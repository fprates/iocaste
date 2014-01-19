package org.iocaste.svg.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class SVG extends AbstractServiceInterface {
    public static final String SERVICENAME = "/iocaste-svg/services.html";
    
    public SVG(Function function) {
        initService(function, SERVICENAME);
    }
    
    public static final SVGData instance() {
        return new SVGData();
    }
    
    public final String compile(SVGData svgdata) {
        Message message = new Message();
        
        message.setId("compile");
        message.add("data", svgdata);
        return call(message);
    }
}
