package org.iocaste.protocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;

public abstract class ServerServlet extends HttpServlet {
    private static final long serialVersionUID = 7408336035974886402L;
    private SessionFactory sessionFactory;
    private Map<String, Function> functions;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    
    public ServerServlet() {
        functions = new HashMap<String, Function>();
        sessionFactory = HibernateListener.getSessionFactory();
        config();
    }
    
    /*
     * 
     * Getters
     * 
     */
    
    private final String getUrl() {
        String url = new StringBuffer(req.getScheme()).append("://")
            .append(req.getServerName()).append(":")
            .append(req.getServerPort())
            .append(req.getContextPath())
            .append(req.getServletPath()).toString();
        
        return url;
    }
    
    /*
     * 
     * Others
     * 
     */
    
    @Override
    protected final void doGet(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected final void doPost(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.req = req;
        this.resp = resp;
        
        try {
            entry();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void preRun(Message message) throws Exception { }
    
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
    
    protected final Service serviceInstance() {
        return new Service(req.getSession().getId(), getUrl());
    }
    
    protected final void configureStreams(Service service) throws IOException {
        service.setInputStream(req.getInputStream());
        service.setOutputStream(resp.getOutputStream());
    }
}
