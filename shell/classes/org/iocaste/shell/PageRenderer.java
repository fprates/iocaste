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
    private Map<String, PagePos> apps;
    private HtmlRenderer renderer;
    
    public PageRenderer() {
        apps = new HashMap<String, PagePos>();
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
            Map<String, ?> parameters, PagePos pagepos) throws Exception {
        Message message = new Message();
        
        message.setId("exec_action");
        message.add("view", pagepos.view);
        message.setSessionid(sessionid);
        
        for (String name : parameters.keySet())
            message.add(name, parameters.get(name));
            
        return (ControlData)Service.callServer(
                composeUrl(pagepos.app), message);
    }
    
    /**
     * 
     * @param resp
     * @param url
     * @param page
     * @throws Exception
     */
    private final void render(
            HttpServletRequest req, HttpServletResponse resp, PagePos pagepos)
            throws Exception {
        String[] text;
        String appname;
        String pagename;
        Message message = new Message();
        PrintWriter writer = resp.getWriter();
        
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        
        appname = (pagepos.view == null)? "" : pagepos.view.getAppName();
        pagename = (pagepos.view == null)? "" : pagepos.view.getPageName();
        
        if (!appname.equals(pagepos.app) || !pagename.equals(pagepos.page)) {
            message.setId("get_view_data");
            message.add("app", pagepos.app);
            message.add("page", pagepos.page);
            message.setSessionid(req.getSession().getId());
            
            pagepos.view = (ViewData)Service.callServer(composeUrl(pagepos.app), message);
        }
        
        text = renderer.run(pagepos.view);

        if (text != null)
            for (String line : text)
                writer.println(line);
                
        writer.close();
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
     * @param iocaste
     * @param req
     * @param pagepos
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private final void processController(Iocaste iocaste,
            HttpServletRequest req, PagePos pagepos) throws Exception {
        Map<String, ?> parameters;
        
        if (ServletFileUpload.isMultipartContent(req)) {
            parameters = processMultipartContent(req, pagepos);
            pagepos.pagetrack = (String)parameters.get("pagetrack");
        } else {
            parameters = new HashMap<String, String[]>();
            parameters.putAll(req.getParameterMap());
            
            if (parameters.size() == 0)
                return;
            
            pagepos.pagetrack = ((String[])parameters.get("pagetrack"))[0];
            parameters.remove("pagetrack");
        }

        pagepos.control = callController(
                req.getSession().getId(), parameters, pagepos);
        
        if (pagepos.control == null)
            return;
        
        pagepos.view = pagepos.control.getViewData();
        
        if (pagepos.control.getApp() != null) {
            pagepos.app = pagepos.control.getApp();
            pagepos.page = pagepos.control.getPage();
        } else {
            pagepos.app = pagepos.view.getAppName();
            pagepos.page = pagepos.view.getPageName();
        }
        
        renderer.setMessageText(pagepos.control.getTranslatedMessage());
        renderer.setMessageType(pagepos.control.getMessageType());
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
            PagePos pagepos) throws Exception {
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
     * @param req
     * @param resp
     * @throws Exception
     */
    private final void entry(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Iocaste iocaste = new Iocaste(this);
        PagePos pagepos = apps.get(sessionid);
        
        if (pagepos == null) {
            pagepos = new PagePos();
            apps.put(sessionid, pagepos);
            renderer.setUsername("Not connected");
        }
        
        processController(iocaste, req, pagepos);
        
        if (!iocaste.isConnected()) {
            pagepos.app = LOGIN_APP;
            pagepos.page = "authentic";
            renderer.setUsername("Not connected");
        }
        
        render(req, resp, pagepos);
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
    public ViewData view;
    public String pagetrack;
    public ControlData control;
}