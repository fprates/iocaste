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
    private Map<String, String> apps;
    private ElementRenderer renderer;
    
    public PageRenderer() {
        apps = new HashMap<String, String>();
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
    private final void render(HttpServletResponse resp, String url,
            String page) throws Exception {
        ViewData vdata;
        String[] text;
        Message message = new Message();
        PrintWriter writer = resp.getWriter();
        
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        
        message.setId("get_view_data");
        message.add("page", page);
        vdata = (ViewData)Service.callServer(url, message);
        
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
    
    private final void entry(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        String page;
        ControlData controldata;
        Iocaste iocaste = new Iocaste(this);
        String action = req.getParameter("action");
        String app = apps.get(sessionid);
        
        if ((!iocaste.isConnected()) && (app == null)) {
            app = LOGIN_APP;
            apps.put(sessionid, app);
            
            page = "authentic";
        } else {
            page = null;
            
            if (action != null) {
                controldata = callController(req, composeUrl(app));
                
                app = controldata.getApp();

                renderer.setMessageText(controldata.getMessageText());
                renderer.setMessageType(controldata.getMessageType());
                
                if (app != null) {
                    apps.remove(sessionid);
                    apps.put(sessionid, app);
                } else {
                    app = apps.get(sessionid);
                }
                
                if (!iocaste.isConnected()) {
                    app = LOGIN_APP;
                    page = "authentic";
                    
                    apps.remove(sessionid);
                    apps.put(sessionid, app);
                } else {
                    page = controldata.getPage();
                }
            } else {
                if (app.equals(LOGIN_APP))
                    page = "authentic";
            }
        }
        
        render(resp, composeUrl(app), page);
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
        sessionid = req.getSession().getId();
        servername = new StringBuffer(req.getScheme()).append("://").
                        append(req.getServerName()).append(":").
                        append(req.getServerPort()).toString();
        
        try {
            entry(req, resp);
        } catch (Exception e) {
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
