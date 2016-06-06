package org.iocaste.external.gateway;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.iocaste.external.Services;
import org.iocaste.external.common.MessageExtractor;
import org.iocaste.protocol.AbstractIocasteServlet;
import org.iocaste.protocol.AbstractService;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = 3691277940360019320L;

    @Override
    protected void config() {
        register(new Services());
        authorize("connect", null);
    }
    
    @Override
    protected final Message getMessage(String sessionid, Service service)
            throws Exception {
        Message message = super.getMessage(sessionid, service);
        if (message.getSessionid() == null)
            message.setSessionid(sessionid);
        return message;
    }
    
    @Override
    protected final Service serviceInstance(String sessionid, String url) {
        return new ExternalService(sessionid, url);
    }

}

class ExternalService extends AbstractService {

    public ExternalService(String sessionid, String urlname) {
        super(sessionid, urlname);
    }

    @Override
    public Message getMessage() throws Exception {
        BufferedReader reader = null;
//        Writer writer = null;
        MessageExtractor extractor;
        
        reader = new BufferedReader(
                new InputStreamReader(getInputStream()));
//        writer = new BufferedWriter(
//                new OutputStreamWriter(getOutputStream()));
        
        extractor = new MessageExtractor();
        return extractor.execute(reader);
    }
}