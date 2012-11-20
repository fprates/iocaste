package org.iocaste.external.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WebService {
    private String wsdl, wsurl;
    private List<String> content;
    
    public WebService(String wsdl, String wsurl) {
        String line;
        InputStreamReader sreader;
        BufferedReader breader;
        InputStream is = getClass().getResourceAsStream(wsdl);
        
        if (is == null)
            throw new RuntimeException("undefined WSDL.");
        
        this.wsdl = wsdl;
        this.wsurl = wsurl;
        sreader = new InputStreamReader(is);
        breader = new BufferedReader(sreader);
        content = new ArrayList<>();
        
        try {
            while ((line = breader.readLine()) != null)
                content.add(line);
            breader.close();
            sreader.close();
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public final String getWSDL() {
        return wsdl;
    }
    
    public final List<String> getWSDLContent() {
        return content;
    }
    
    public final String getWSURL() {
        return wsurl;
    }
}
