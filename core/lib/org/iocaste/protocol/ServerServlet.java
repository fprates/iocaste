package org.iocaste.protocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ServerServlet extends HttpServlet {
    private static final long serialVersionUID = 7408336035974886402L;
    private Map<String, Function> functions;
    private Map<String, Map<String, Function>> sfunctions;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Map<String, Map<String, Object[]>> authorized;
    private boolean singleton;
    
    public ServerServlet() {
        functions = new HashMap<>();
        sfunctions = new HashMap<>();
        authorized = new HashMap<>();
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
    protected final void configureStreams(Service service) throws IOException {
        service.setInputStream(req.getInputStream());
        service.setOutputStream(resp.getOutputStream());
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doPost(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Message message;
        Function function;
        Service service;
        String functionid, complexid;
        String sessionid = req.getSession().getId();
        
        this.req = req;
        this.resp = resp;
        
        service = new Service(sessionid, getUrl());
        configureStreams(service);
        
        try {
            message = service.getMessage();
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }
        
        try {
            preRun(message);
            functionid = message.getId();
            if (functionid == null)
                throw new Exception("Function not specified.");
            
            if (singleton) {
                function = functions.get(functionid);
            } else {
                complexid = message.getSessionid();
                function = sfunctions.get(complexid).get(functionid);
            }
            
            if (function == null)
                throw new Exception(new StringBuilder("Function \"").
                        append(functionid).
                        append("\" not registered.").toString());
            
            function.setServletContext(getServletContext());
            function.setServerName(getServerName());
            function.setSessionid(sessionid);
            function.setAuthorizedCall(isAuthorized(message));
            
            service.messageReturn(message, function.run(message));
        } catch (Exception e) {
            service.messageException(message, e);
        } finally {
            req.getSession().invalidate();
        }
    }
    
    /**
     * 
     * @return
     */
    public final String getServerName() {
        return new StringBuffer(req.getScheme()).append("://")
                .append(req.getServerName()).append(":")
                .append(req.getServerPort()).toString();
    }
    
    /**
     * 
     * @return
     */
    private final String getUrl() {
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
        boolean connected;
        Message test;
        String url;
        
        if (isAuthorized(message))
            return;
        
        test = new Message();
        url = new StringBuffer(getServerName())
            .append(Iocaste.SERVERNAME).toString();
        
        test.setId("is_connected");
        test.add("sessionid", message.getSessionid());
        test.setSessionid(message.getSessionid());
        
        connected = (Boolean)Service.callServer(url, test);
        
        if (!connected)
            throw new IocasteException("protocol.pre-run: invalid session");
    }
    
    /**
     * 
     * @param function
     */
    protected void register(Function function) {
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
        
        for (String method : function.getMethods())
            functions.put(method, function);
        
        sfunctions.put(sessionid, functions);
    }
    
    /**
     * 
     * @param singleton
     */
    protected final void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }
}
