package org.iocaste.protocol;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;

public abstract class ServerServlet extends IocasteServlet {
    private static final long serialVersionUID = 7408336035974886402L;
    private SessionFactory sessionFactory;
    private Map<String, Function> functions;
    
    public ServerServlet() {
        functions = new HashMap<String, Function>();
        sessionFactory = HibernateListener.getSessionFactory();
        config();
    }

    protected void preRun(Message message) throws Exception { }
    
    @Override
    protected final void entry() throws Exception {
        Message message;
        Function function;
        Service service = serviceInstance();
        
        configureStreams(service);
        
        try {
            message = service.getMessage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        
        try {
            preRun(message);
            function = functions.get(message.getId());
            service.messageReturn(message, function.run(message));
        } catch (Exception e) {
            service.messageException(message, e);
            return;
        }
    }
    
    private final void addFunction(String name, Function function) {
        functions.put(name, function);
        function.setSessionFactory(sessionFactory);
    }
    
    protected void register(AbstractFunction function) {
        for (String method : function.getMethods())
            addFunction(method, function);
    }
    
    protected abstract void config();
}
