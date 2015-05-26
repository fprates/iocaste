package org.iocaste.external;

import java.util.Map;

import org.iocaste.protocol.AbstractIocasteServlet;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = 3773758575369740138L;
    
    @Override
    protected void config() {
        register(new Services());
        authorize("connect", null);
    }
    
    protected final Message getMessage(
            String sessionid, Service service, Map<String, String[]> parameters)
                    throws Exception {
        Message message;
        
        message = super.getMessage(sessionid, service, parameters);
        message.setSessionid(sessionid);
        return message;
    }

}
