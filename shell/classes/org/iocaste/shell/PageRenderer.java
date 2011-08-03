package org.iocaste.shell;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.ViewData;

public class PageRenderer extends HttpServlet implements Function {
    private static final long serialVersionUID = -8143025594178489781L;
    private static final String LOGIN_APP = "iocaste-login";
    private String sessionid;
    private String servername;
    private Map<String, PagePos> apps;
    private ElementRenderer renderer;
    
    public PageRenderer() {
        apps = new HashMap<String, PagePos>();
        renderer = new ElementRenderer();
    }
    
    /**
     * 
     * @param req
     * @param url
     * @return
     * @throws Exception
     */
    private final ControlData callController(
            HttpServletRequest req, String url) throws Exception {
        String paramname;
        Message message = new Message();
        
        message.setId("exec_action");
        message.setSessionid(req.getSession().getId());
        
        for (Object obj : req.getParameterMap().keySet()) {
            paramname = (String)obj;
            message.add(paramname, req.getParameter(paramname));
        }
            
        return (ControlData)Service.callServer(url, message);
    }
    
    /**
     * 
     * @param resp
     * @param url
     * @param page
     * @throws Exception
     */
    private final void render(HttpServletResponse resp, PagePos pagepos)
            throws Exception {
        ViewData vdata;
        String[] text;
        Message message = new Message();
        PrintWriter writer = resp.getWriter();
        
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        
        message.setId("get_view_data");
        message.add("page", pagepos.page);
        vdata = (ViewData)Service.callServer(composeUrl(pagepos.app), message);
        
        if (vdata.getContainer() == null)
            text = vdata.getLines();
        else
            text = renderer.run(vdata);

        for (String line : text)
            writer.println(line);
                
        writer.close();
    }
    
    private final String composeUrl(String app) {
        return new StringBuffer(servername).append("/").
                append(app).append("/view.html").toString();
    }
    
    private final ControlData processController(Iocaste iocaste,
            HttpServletRequest req, PagePos pagepos) throws Exception {
        ControlData controldata = callController(req, composeUrl(pagepos.app));
        
        renderer.setMessageText(controldata.getTranslatedMessage());
        renderer.setMessageType(controldata.getMessageType());
        
        return controldata;
    }
    
    private final void entry(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        ControlData controldata = null;
        Iocaste iocaste = new Iocaste(this);
        PagePos pagepos = apps.get(sessionid);
        
        if (!iocaste.isConnected()) {
            if (pagepos == null) {
                pagepos = new PagePos();
                apps.put(sessionid, pagepos);
            } else {
                controldata = processController(iocaste, req, pagepos);
            }
        } else {
            controldata = processController(iocaste, req, pagepos);
        }
        
        if (!iocaste.isConnected() || controldata == null) {
            pagepos.app = LOGIN_APP;
            pagepos.page = "authentic";
            
            renderer.setUsername("Not connected");
        } else {
            if (controldata.getApp() != null) {
                pagepos.app = controldata.getApp();
                pagepos.page = controldata.getPage();
            }
            
            renderer.setUsername(iocaste.getUsername());
        }
        
        render(resp, pagepos);
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PagePos pagepos;
        sessionid = req.getSession().getId();
        servername = new StringBuffer(req.getScheme()).append("://").
                        append(req.getServerName()).append(":").
                        append(req.getServerPort()).toString();
        
        try {
            entry(req, resp);
        } catch (Exception e) {
            pagepos = apps.get(sessionid);
            
            if (pagepos != null) {
                pagepos.app = LOGIN_APP;
                pagepos.page = "authentic";
            }
            
            throw new ServletException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#getMethods()
     */
    @Override
    public final Set<String> getMethods() {
        return null;
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
     * @see org.iocaste.protocol.Function#setSessionFactory(
     *     org.hibernate.SessionFactory)
     */
    @Override
    public final void setSessionFactory(SessionFactory sessionFactory) { }

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
    public void setSessionid(String sessionid) { }

}

class PagePos {
    public String app;
    public String page;
}