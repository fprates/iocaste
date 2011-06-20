package org.iocaste.protocol;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class IocasteServlet extends HttpServlet {
    private static final long serialVersionUID = 6054291682722402756L;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    
    /*
     * Others
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
    
    private final String getUrl() {
        String url = new StringBuffer(req.getScheme()).append("://")
            .append(req.getServerName()).append(":")
            .append(req.getServerPort())
            .append(req.getContextPath())
            .append(req.getServletPath()).toString();
         
        return url;
    }
    
    protected abstract void entry() throws Exception;
    
    protected final void redirect(String url) throws IOException {
        resp.sendRedirect(url);
    }
    
    protected final Service serviceInstance() throws IOException {
        return new Service(req.getSession().getId(), getUrl());
    }
    
    protected final void configureStreams(Service service) throws IOException {
        service.setInputStream(req.getInputStream());
        service.setOutputStream(resp.getOutputStream());
    }
}
