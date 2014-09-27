package org.iocaste.external;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.protocol.AbstractIocasteServlet;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = 3773758575369740138L;
    private Set<String> sessions;
    
    public Servlet() {
        sessions = new HashSet<>();
    }
    
    @Override
    protected void config() {
        register(new Services());
        authorize("dispatch", null);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected Message getMessage(String sessionid, Service service,
            Map<String, String[]> parameters) throws Exception {
        String session;
        Message message;
        ObjectInputStream ois;
        BufferedInputStream bis;
        Map<String, Object> map; 
        
        bis = new BufferedInputStream(service.getInpustStream());
        ois = new ObjectInputStream(bis);
        map = (Map<String, Object>)ois.readObject();
        
        session = new StringBuilder(sessionid).append(":").
                append(sessions.size()).toString();
        sessions.add(session);
        
        message = new Message("dispatch");
        message.add("app", map.get("app"));
        message.add("function", map.get("function"));
        message.add("stream", map.get("stream"));
        message.add("username", map.get("username"));
        message.add("secret", map.get("secret"));
        message.setSessionid(session);
        return message;
    }

}
