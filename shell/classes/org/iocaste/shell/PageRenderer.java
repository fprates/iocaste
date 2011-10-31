package org.iocaste.shell;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.ViewData;

public class PageRenderer extends HttpServlet implements Function {
    private static final long serialVersionUID = -8143025594178489781L;
    private static final String LOGIN_APP = "iocaste-login";
    private static final int MEMORY_THRESOLD = 512*1024; 
    private String sessionid;
    private String servername;
    private Map<String, SessionContext> apps;
    private HtmlRenderer renderer;
    
    public PageRenderer() {
        apps = new HashMap<String, SessionContext>();
        renderer = new HtmlRenderer();
    }
    
    /**
     * 
     * @param req
     * @param url
     * @return
     * @throws Exception
     */
    private final ControlData callController(String sessionid,
            Map<String, ?> parameters, PageContext pagectx) throws Exception {
        Message message = new Message();
        
        message.setId("exec_action");
        message.add("view", pagectx.getViewData());
        message.setSessionid(sessionid);
        
        for (String name : parameters.keySet())
            message.add(name, parameters.get(name));
            
        return (ControlData)Service.callServer(
                composeUrl(pagectx.getAppContext().getName()), message);
    }
    
    /**
     * 
     * @param app
     * @return
     */
    private final String composeUrl(String app) {
        return new StringBuffer(servername).append("/").
                append(app).append("/view.html").toString();
    }
    
    /**
     * 
     * @param sessionid
     * @param appname
     * @param pagename
     * @return
     */
    private final PageContext createPageContext(String sessionid,
            String appname, String pagename) {
        SessionContext sessionctx = (apps.containsKey(sessionid))?
                apps.get(sessionid) : new SessionContext();
        AppContext appctx = (sessionctx.contains(appname))?
                sessionctx.getAppContext(appname) : new AppContext(appname);
        PageContext pagectx = new PageContext(pagename);
        
        pagectx.setAppContext(appctx);
        appctx.put(pagename, pagectx);
        sessionctx.put(appname, appctx);
        
        apps.put(sessionid, sessionctx);
        
        return pagectx;
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
    
    /**
     * 
     * @param req
     * @param resp
     * @throws Exception
     */
    private final void entry(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        PageContext pagectx;
        Iocaste iocaste = new Iocaste(this);
        
        if (apps.containsKey(sessionid)) {
            pagectx = getPageContext(req, sessionid);
            
            if (pagectx == null) {
                pagectx = getPageContext(sessionid, LOGIN_APP, "authentic");
                renderer.setUsername("not.connected");
            }
            
            pagectx = processController(iocaste, req, pagectx);
        } else {
            pagectx = createPageContext(sessionid, LOGIN_APP, "authentic");
            renderer.setUsername("not.connected");
        }
        
        render(resp, pagectx);
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
     * @param req
     * @param sessionctx
     * @return
     */
    @SuppressWarnings("unchecked")
    private final PageContext getPageContext(HttpServletRequest req,
            String sessionid) throws Exception {
        String[] pageparse;
        ServletFileUpload fileupload;
        List<FileItem> files;
        String pagetrack = null;
        
        if (ServletFileUpload.isMultipartContent(req)) {
            fileupload = new ServletFileUpload(new DiskFileItemFactory());
            files = fileupload.parseRequest(req);
            
            if (files == null)
                return null;
            
            for (FileItem fileitem : files) {
                pagetrack = null;
                
                if (!fileitem.isFormField())
                    continue;
            
                pagetrack = fileitem.getFieldName();
                
                if (!pagetrack.equals("pagetrack"))
                    continue;
                
                break;
            }
        } else {
            pagetrack = req.getParameter("pagetrack");
        }
        
        if (pagetrack == null)
            return null;
        
        pageparse = pagetrack.split("\\.", 2);
        
        return getPageContext(sessionid, pageparse[0], pageparse[1]);
    }
    
    /**
     * 
     * @param sessionid
     * @param appname
     * @param pagename
     * @return
     */
    private final PageContext getPageContext (String sessionid, String appname,
            String pagename) {
        AppContext appctx = apps.get(sessionid).getAppContext(appname);
        
        return (appctx == null)? null : appctx.getPageContext(pagename);
    }
    
    /**
     * 
     * @param iocaste
     * @param req
     * @param pagepos
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private final PageContext processController(Iocaste iocaste,
            HttpServletRequest req, PageContext pagectx) throws Exception {
        PageContext pagectx_;
        Map<String, ?> parameters;
        ControlData control;
        String appname;
        String pagename;
        
        if (ServletFileUpload.isMultipartContent(req)) {
            parameters = processMultipartContent(req, pagectx);
        } else {
            parameters = new HashMap<String, String[]>();
            parameters.putAll(req.getParameterMap());
            
            if (parameters.size() == 0)
                return pagectx;
        }

        if (parameters.containsKey("pagetrack"))
            parameters.remove("pagetrack");
            
        control = callController(sessionid, parameters, pagectx);
        
        if (control == null)
            return pagectx;
        
        renderer.setMessageText(control.getTranslatedMessage());
        renderer.setMessageType(control.getMessageType());
        renderer.setUsername((iocaste.isConnected())?
                iocaste.getUsername():"Not connected");
        
        appname = control.getApp();
        if (appname == null)
            appname = pagectx.getAppContext().getName();
        
        pagename = control.getPage();
        if (pagename == null)
            pagename = pagectx.getName();
        
        pagectx_ = getPageContext(sessionid, appname, pagename);
        
        if (pagectx_ == null)
            pagectx_ = createPageContext(sessionid, appname, pagename);
        
        pagectx_.setControlData(control);
        pagectx_.setReloadableView(control.isReloadableView());
        
        return pagectx_;
    }
    
    /**
     * 
     * @param req
     * @param pagectx
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private final Map<String, ?> processMultipartContent(HttpServletRequest req,
            PageContext pagectx) throws Exception {
        DiskFileItemFactory factory;
        ServletFileUpload fileupload;
        List<FileItem> files;
        String path;
        String fieldname;
        String filename;
        Element[] elements;
        Map<String, String> parameters;
        
        factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESOLD);
        path = req.getSession().getServletContext().
                getRealPath("WEB-INF/data");
        factory.setRepository(new File(path));
        fileupload = new ServletFileUpload(factory);
        files = fileupload.parseRequest(req);
        
        parameters = new HashMap<String, String>();
        elements = pagectx.getViewData().getMultipartElements();
        
        for (Element element : elements) {
            for (FileItem fileitem : files) {
                fieldname = fileitem.getFieldName();
                
                if (fileitem.isFormField()) {
                    parameters.put(fieldname, fileitem.getString());
                    continue;
                }
                
                if (!fieldname.equals(element.getName()))
                    continue;
                
                filename = fileitem.getName();
                fileitem.write(new File(element.getDestiny(), filename));
                
                parameters.put(fieldname, filename);
            }
        }
        
        return parameters;
    }
    
    /**
     * 
     * @param resp
     * @param pagectx
     * @throws Exception
     */
    private final void render(HttpServletResponse resp, PageContext pagectx)
            throws Exception {
        String[] text;
        AppContext appctx;
        ViewData viewdata;
        Message message = new Message();
        PrintWriter writer = resp.getWriter();
        ControlData control = pagectx.getControlData();
        
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        viewdata = pagectx.getViewData();
        
        if (viewdata == null || pagectx.isReloadableView()) {
            appctx = pagectx.getAppContext();
            
            message.setId("get_view_data");
            message.add("app", appctx.getName());
            message.add("page", pagectx.getName());
            message.setSessionid(sessionid);
            
            if (control != null)
                message.add("parameters", control.getParameters());
            
            viewdata = (ViewData)Service.callServer(
                    composeUrl(appctx.getName()), message);
            pagectx.setViewData(viewdata);
        }
        
        if ((control != null) && (viewdata.equals(control.getViewData())))
        	text = renderer.run(control.getViewData());
        else
        	text = renderer.run(pagectx.getViewData());

        if (text != null)
            for (String line : text)
                writer.println(line);
                
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
     * @see org.iocaste.protocol.Function#serviceInstance(java.lang.String)
     */
    @Override
    public final Service serviceInstance(String path) {
        String url = new StringBuffer(servername).append(path).toString();
        
        return new Service(sessionid, url);
    }

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
     * @see org.iocaste.protocol.Function#setSessionid(java.lang.String)
     */
    @Override
    public void setSessionid(String sessionid) { }

}