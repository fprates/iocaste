package org.iocaste.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.shell.common.View;

public abstract class AbstractRenderer extends HttpServlet implements Function {
    private static final long serialVersionUID = -7711799346205632679L;
    private static final boolean NEW_SESSION = false;
    private static final boolean KEEP_SESSION = true;
    private static final String STD_CONTENT = "text/html";
    private String servername;
    
    /**
     * 
     * @param resp
     * @param view
     */
    private final void configResponse(HttpServletResponse resp, View view) {
        String contenttype = view.getContentType();
        
        resp.setContentType((contenttype == null)? STD_CONTENT : contenttype);
        resp.setCharacterEncoding("UTF-8");
        
        for (String key : view.getHeaderKeys())
            resp.setHeader(key, view.getHeader(key));
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
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
    
    /**
     * 
     * @param sessionid
     * @param logid
     * @return
     */
    protected final String getComplexId(String sessionid, int logid) {
        return new StringBuilder(sessionid).append(":").append(logid).
                toString();
    }
    
    /**
     * 
     * @param pagetrack
     * @return
     */
    protected static final int getLogid(String pagetrack) {
        String[] parsed = pagetrack.split(":");
        
        return Integer.parseInt(parsed[2]);
    }
    
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
    protected final String getServerName() {
        return servername;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#isAuthorizedCall()
     */
    @Override
    public final boolean isAuthorizedCall() {
        return false;
    }
    
    /**
     * 
     * @param renderer
     * @param view
     * @param tracking
     * @throws Exception
     */
    protected final void render(HtmlRenderer renderer, HttpServletResponse resp,
            View view, TrackingData tracking) throws Exception {
        byte[] content;
        OutputStream os;
        String[] text;
        PrintWriter writer;
        
        if (view == null)
            throw new Exception("Cannot render a null vision.");
        
        /*
         * reseta o servlet response para que possamos
         * usar OutputStream novamente (já é utilizado em
         * Service.callServer()).
         */
        
        if (view.getContentType() != null) {
            resp.reset();
            configResponse(resp, view);
            os = resp.getOutputStream();
            
            content = view.getContent();
            if (content != null) {
                os.write(content);
                resp.setContentLength(content.length);
            } else {
                for (String line : view.getPrintLines())
                    os.write(line.getBytes());
            }
            
            os.flush();
            os.close();
            
            return;
        }
        
        configResponse(resp, view);
        text = renderer.run(view, tracking);
        writer = resp.getWriter();
        if (text != null)
            for (String line : text)
                writer.println(line);
        
        writer.flush();
        writer.close();
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
        
        return new Service(null, url);
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setSessionid(java.lang.String)
     */
    @Override
    public final void setSessionid(String sessionid) { }

}
