package org.iocaste.external.service;

import java.util.Map;

public class WebService {
    private String wsdl, wsurl;
    private Map<String, Map<String, String[]>> wsdata;
    
    public WebService(String wsdl, String wsurl) {
        this.wsdl = wsdl;
        this.wsurl = wsurl;
    }
    
    public final Map<String, Map<String, String[]>> getData() {
        return wsdata;
    }
    
    public final String getWSDL() {
        return wsdl;
    }
    
    public final String getWSURL() {
        return wsurl;
    }
    
    public final void setData(Map<String, Map<String, String[]>> wsdata) {
        this.wsdata = wsdata;
    }
}
