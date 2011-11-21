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
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Map<String, Map<String, Object[]>> authorized;
    
    public ServerServlet() {
        functions = new HashMap<String, Function>();
        authorized = new HashMap<String, Map<String, Object[]>>();
        
        config();
    }
    
    /*
     * 
     * Getters
     * 
     */
    
    /**
     * 
     * @return
     */
    private final String getServerName() {
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
    
    /*
     * 
     * Others
     * 
     */
    
    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doGet(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
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
        String functionid;
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
            
            function = functions.get(functionid);
            if (function == null)
                throw new Exception("Function \""+functionid+"\" not registered.");
            
            function.setServletContext(getServletContext());
            function.setServerName(getServerName());
            function.setSessionid(sessionid);
            
            service.messageReturn(message, function.run(message));
        } catch (Exception e) {
            service.messageException(message, e);
        } finally {
            req.getSession().invalidate();
        }
    }

    /**
     * 
     * @param message
     * @return
     */
    private final boolean isAuthorized(Message message) {
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
            throw new InvalidSessionException();
    }
    
    /**
     * 
     * @param name
     * @param function
     */
    private final void addFunction(String name, Function function) {
        functions.put(name, function);
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
     * @param function
     */
    protected void register(Function function) {
        for (String method : function.getMethods())
            addFunction(method, function);
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
}
