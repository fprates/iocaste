package org.iocaste.protocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractIocasteServlet extends HttpServlet {
    private static final long serialVersionUID = 7408336035974886402L;
    private Map<String, Function> functions;
    private Map<String, Map<String, Function>> sfunctions;
    private Map<String, Map<String, Object[]>> authorized;
    private Map<String, HttpServletRequest> requests;
    private Set<String> disconnectedops;
    private String servername;
    private boolean singleton;
    
    public AbstractIocasteServlet() {
        functions = new HashMap<>();
        sfunctions = new HashMap<>();
        authorized = new HashMap<>();
        requests = new HashMap<>();
        disconnectedops = new HashSet<>();
        singleton = true;
        config();
    }
    
    /**
     * 
     * @param function
     * @param parameters
     */
    protected final void authorize(String function,
            Map<String, Object[]> parameters) {
        authorized.put(function, parameters);
    }
    
    /**
     * 
     */
    protected abstract void config();
    
    /**
     * 
     * @param service
     * @throws IOException
     */
    protected final void configureStreams(Service service, Context context)
            throws IOException {
        service.setInputStream(context.req.getInputStream());
        service.setOutputStream(context.resp.getOutputStream());
    }

    /**
     * 
     * @param op
     */
    protected final void disconnectedop(String op) {
        disconnectedops.add("disconnected_operation");
    }
    
    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Message message;
        Function function;
        Service service;
        Context context;
        String functionid, complexid;
        String sessionid = req.getSession().getId();

        setServerName(req);
        service = serviceInstance(sessionid, getUrl(req));
        context = new Context();
        context.req = req;
        context.resp = resp;
        message = null;

        try {
            configureStreams(service, context);
            message = getMessage(sessionid, service);
            complexid = message.getSessionid();
            requests.put(complexid, req);
            preRun(message);
            
            functionid = message.getId();
            if (functionid == null)
                throw new Exception("Function not specified.");
            
            if (singleton)
                function = functions.get(functionid);
            else
                function = sfunctions.get(complexid).get(functionid);
            
            if (function == null)
                throw new IocasteException(
                        "Function \"%s\" not registered.", functionid);
            
            function.setServletContext(getServletContext());
            function.setSessionid(complexid);
            function.setAuthorizedCall(isAuthorized(message));
            
            service.messageReturn(message, function.run(message));
        } catch (Exception e) {
            if (message == null)
                message = new Message(null);
            
            service.messageException(message, e);
        } finally {
            req.getSession().invalidate();
        }
    }
    
    protected Message getMessage(String sessionid, Service service)
            throws Exception {
        return service.getMessage();
    }
    
    /**
     * 
     * @param req
     * @return
     */
    public final String getServerName() {
        return servername;
    }
    
    /**
     * 
     * @param req
     * @return
     */
    private final String getUrl(HttpServletRequest req) {
        return new StringBuffer(getServerName())
            .append(req.getContextPath())
            .append(req.getServletPath()).toString();
    }

    /**
     * 
     * @param message
     * @return
     */
    protected final boolean isAuthorized(Message message) {
        Map<String, Object[]> authparameters;
        Map<String, Object> msgparameters;
        String id = message.getId();
        
        if (!authorized.containsKey(id))
            return false;

        authparameters = authorized.get(id);
        if (authparameters == null)
            return true;
        
        msgparameters = message.getParameters();
        for (String name : authparameters.keySet())
            for (Object object : authparameters.get(name))
                if (object.equals(msgparameters.get(name)))
                    return true;
        
        return false;
    }
    
    /**
     * 
     * @param op
     * @return
     */
    protected final boolean isDisconnectedOp(String op) {
        return disconnectedops.contains(op);
    }
    
    /**
     * 
     * @param sessionid
     * @return
     */
    protected final boolean isSessionRegistered(String sessionid) {
        return sfunctions.containsKey(sessionid);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    protected void preRun(Message message) throws Exception {
        Service service;
        boolean connected;
        Message test;
        String url, sessionid;
        
        if (isAuthorized(message))
            return;
        
        sessionid = message.getSessionid();
        test = new Message("is_connected");
        url = new StringBuffer(getServerName()).append(Iocaste.SERVERNAME).
                toString();
        test.add("sessionid", sessionid);
        test.setSessionid(sessionid);
        
        service = serviceInstance(sessionid, url);
        connected = (Boolean)service.call(test);
        
        if (!connected)
            throw new IocasteException("protocol.pre-run: invalid session");
    }
    
    /**
     * 
     * @param function
     */
    protected void register(Function function) {
        function.set(this);
        for (String method : function.getMethods())
            functions.put(method, function);
    }
    
    /**
     * 
     * @param sessionid
     * @param function
     */
    protected void register(String sessionid, Function function) {
        Map<String, Function> functions = new HashMap<>();
        
        function.set(this);
        for (String method : function.getMethods())
            functions.put(method, function);
        
        sfunctions.put(sessionid, functions);
    }
    
    protected Service serviceInstance(String sessionid, String url) {
        return new StandardService(sessionid, url);
    }
    
    protected final void setServerName(HttpServletRequest req) {
        servername = new StringBuffer(req.getScheme()).append("://").
                append("127.0.0.1:").append(req.getLocalPort()).toString();
    }
    
    /**
     * 
     * @param singleton
     */
    protected final void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }
}

class Context {
    public HttpServletRequest req;
    public HttpServletResponse resp;
}