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
        message.add("view", pagectx.view);
        message.setSessionid(sessionid);
        
        for (String name : parameters.keySet())
            message.add(name, parameters.get(name));
            
        return (ControlData)Service.callServer(
                composeUrl(pagectx.app), message);
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
        PageContext pagectx;
        sessionid = req.getSession().getId();
        servername = new StringBuffer(req.getScheme()).append("://").
                        append(req.getServerName()).append(":").
                        append(req.getServerPort()).toString();
        
        try {
            entry(req, resp);
        } catch (Exception e) {
            try {
                pagectx = getPageContext(req, apps.get(sessionid));
            } catch (Exception page_ex) {
                throw new ServletException(page_ex);
            }
            
            if (pagectx != null) {
                pagectx.app = LOGIN_APP;
                pagectx.page = "authentic";
            }
            
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
        AppContext appctx;
        Iocaste iocaste = new Iocaste(this);
        SessionContext sessionctx = apps.get(sessionid);
        
        if (sessionctx != null) {
            pagectx = getPageContext(req, sessionctx);
        } else {
            pagectx = new PageContext();
            appctx = new AppContext();
            sessionctx = new SessionContext();
            
            appctx.put("authentic", pagectx);
            sessionctx.put(LOGIN_APP, appctx);
            apps.put(sessionid, sessionctx);
            
            renderer.setUsername("Not connected");
        }
        
        processController(iocaste, req, pagectx);
        
        if (!iocaste.isConnected()) {
            pagectx.app = LOGIN_APP;
            pagectx.page = "authentic";
            renderer.setUsername("Not connected");
        }
        
        render(req, resp, pagectx);
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
            SessionContext sessionctx) throws Exception {
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
                if (fileitem.isFormField()) {
                    pagetrack = fileitem.getFieldName();
                    
                    if (!pagetrack.equals("pagetrack")) {
                        pagetrack = null;
                        continue;
                    }
                    
                    break;
                }
                
                if (pagetrack != null)
                    break;
            }
        } else {
            pagetrack = req.getParameter("pagetrack");
        }
        
        if (pagetrack == null)
            return null;
        
        pageparse = pagetrack.split("\\.", 2);
        return sessionctx.getAppContext(pageparse[0]).
                getPageContext(pageparse[1]);
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
    private final void processController(Iocaste iocaste,
            HttpServletRequest req, PageContext pagectx) throws Exception {
        Map<String, ?> parameters;
        
        if (ServletFileUpload.isMultipartContent(req)) {
            parameters = processMultipartContent(req, pagectx);
            pagectx.pagetrack = (String)parameters.get("pagetrack");
        } else {
            parameters = new HashMap<String, String[]>();
            parameters.putAll(req.getParameterMap());
            
            if (parameters.size() == 0)
                return;
            
            pagectx.pagetrack = ((String[])parameters.get("pagetrack"))[0];
            parameters.remove("pagetrack");
        }

        pagectx.control = callController(
                req.getSession().getId(), parameters, pagectx);
        
        if (pagectx.control == null)
            return;
        
        pagectx.view = pagectx.control.getViewData();
        
        if (pagectx.control.getApp() != null) {
            pagectx.app = pagectx.control.getApp();
            pagectx.page = pagectx.control.getPage();
        } else {
            pagectx.app = pagectx.view.getAppName();
            pagectx.page = pagectx.view.getPageName();
        }
        
        renderer.setMessageText(pagectx.control.getTranslatedMessage());
        renderer.setMessageType(pagectx.control.getMessageType());
        renderer.setUsername((iocaste.isConnected())?
                iocaste.getUsername():"Not connected");
    }
    
    /**
     * 
     * @param req
     * @param pagepos
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private final Map<String, ?> processMultipartContent(HttpServletRequest req,
            PageContext pagepos) throws Exception {
        DiskFileItemFactory factory;
        ServletFileUpload fileupload;
        List<FileItem> files;
        String path;
        String fieldname;
        String filename;
        Map<String, String> parameters;
        
        factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESOLD);
        path = req.getSession().getServletContext().
                getRealPath("WEB-INF/data");
        factory.setRepository(new File(path));
        fileupload = new ServletFileUpload(factory);
        files = fileupload.parseRequest(req);
        
        parameters = new HashMap<String, String>();
        
        for (Element element : pagepos.view.getMultipartElements()) {
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
     * @param url
     * @param page
     * @throws Exception
     */
    private final void render(HttpServletRequest req, HttpServletResponse resp,
            PageContext pagectx) throws Exception {
        String[] text;
        String appname;
        String pagename;
        Message message = new Message();
        PrintWriter writer = resp.getWriter();
        
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        
        appname = (pagectx.view == null)? "" : pagectx.view.getAppName();
        pagename = (pagectx.view == null)? "" : pagectx.view.getPageName();
        
        if (!appname.equals(pagectx.app) || !pagename.equals(pagectx.page)) {
            message.setId("get_view_data");
            message.add("app", pagectx.app);
            message.add("page", pagectx.page);
            message.setSessionid(req.getSession().getId());
            
            pagectx.view = (ViewData)Service.callServer(
                    composeUrl(pagectx.app), message);
        }
        
        text = renderer.run(pagectx.view);

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