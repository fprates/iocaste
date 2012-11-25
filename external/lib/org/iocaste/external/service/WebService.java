package org.iocaste.external.service;

import java.net.URL;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;

public class WebService {
    private URL wsdl;
    String wsurl;
    private Map<String, Map<String, ExtendedObject[]>> wsdata;
    
    public WebService(URL wsdl, String wsurl) {
        this.wsdl = wsdl;
        this.wsurl = wsurl;
    }
    
    public final Map<String, Map<String, ExtendedObject[]>> getData() {
        return wsdata;
    }
    
    public final URL getWSDL() {
        return wsdl;
    }
    
    public final String getWSURL() {
        return wsurl;
    }
    
    public final void setData(
            Map<String, Map<String, ExtendedObject[]>> wsdata) {
        this.wsdata = wsdata;
    }
}
