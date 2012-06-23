package org.iocaste.internal;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

public abstract class AbstractRenderer extends HttpServlet implements Function{
    private static final long serialVersionUID = -7711799346205632679L;
    private static final boolean NEW_SESSION = false;
    private static final boolean KEEP_SESSION = true;
    private HtmlRenderer renderer;
    private String sessionid, servername;

    public AbstractRenderer() {
        renderer = new HtmlRenderer();
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        sessionid = req.getSession().getId();
        servername = new StringBuffer(req.getScheme()).append("://").
                        append(req.getServerName()).append(":").
                        append(req.getServerPort()).toString();
        
        try {
            entry(req, resp, NEW_SESSION);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doPost(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        sessionid = req.getSession().getId();
        servername = new StringBuffer(req.getScheme()).append("://").
                        append(req.getServerName()).append(":").
                        append(req.getServerPort()).toString();
        
        try {
            entry(req, resp, KEEP_SESSION);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    /**
     * 
     * @param req
     * @param resp
     * @param keepsession
     * @throws Exception
     */
    protected abstract void entry(HttpServletRequest req,
            HttpServletResponse resp, boolean keepsession) throws Exception;
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#getMethods()
     */
    @Override
    public final Set<String> getMethods() {
        return null;
    }

    /**
     * 
     * @return
     */
    protected final HtmlRenderer getRenderer() {
        return renderer;
    }
    
    /**
     * 
     * @return
     */
    protected final String getServerName() {
        return servername;
    }
    
    /**
     * 
     * @return
     */
    protected final String getSessionId() {
        return sessionid;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#isAuthorizedCall()
     */
    @Override
    public final boolean isAuthorizedCall() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#run(org.iocaste.protocol.Message)
     */
    @Override
    public final Object run(Message message) throws Exception {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setAuthorizedCall(boolean)
     */
    @Override
    public final void setAuthorizedCall(boolean authorized) { }

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setServerName(java.lang.String)
     */
    @Override
    public final void setServerName(String servername) { }

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setServletContext(
     *     javax.servlet.ServletContext)
     */
    @Override
    public final void setServletContext(ServletContext context) { }

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#serviceInstance(java.lang.String)
     */
    @Override
    public final Service serviceInstance(String path) {
        String url = new StringBuffer(servername).append(path).toString();
        
        return new Service(sessionid, url);
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setSessionid(java.lang.String)
     */
    @Override
    public final void setSessionid(String sessionid) { }

}
