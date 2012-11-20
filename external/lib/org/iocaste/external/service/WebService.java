package org.iocaste.external.service;

public class WebService {
    private String wsdl, wsurl;
    
    public WebService(String wsdl, String wsurl) {
        this.wsdl = wsdl;
        this.wsurl = wsurl;
    }
    
    public final String getWSDL() {
        return wsdl;
    }
    
    public final String getWSURL() {
        return wsurl;
    }
}
