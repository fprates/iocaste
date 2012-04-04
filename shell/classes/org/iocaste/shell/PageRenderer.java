package org.iocaste.shell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
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
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.MultipartElement;
import org.iocaste.shell.common.SHLib;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.ViewData;

public class PageRenderer extends HttpServlet implements Function {
    private static final long serialVersionUID = -8143025594178489781L;
    private static final String LOGIN_APP = "iocaste-login";
    private static final String NOT_CONNECTED = "not.connected";
    private static Map<String, List<SessionContext>> apps =
            new HashMap<String, List<SessionContext>>();
    private String sessionid, servername;
    private HtmlRenderer renderer;
    private Map<String, Map<String, String>> style;
    
    public PageRenderer() {
        renderer = new HtmlRenderer();
        style = null;
    }
    
    /**
     * 
     * @param req
     * @param url
     * @return
     * @throws Exception
     */
    private final ViewData callController(String sessionid,
            Map<String, ?> parameters, PageContext pagectx) throws Exception {
        InputStatus status;
        Message message;
        
        status = Controller.validate(pagectx.getViewData(), parameters, this);
        if (status.fatal != null)
            throw new IocasteException(status.fatal);
        
        if (status.error > 0)
            return pagectx.getViewData();
        
        message = new Message();
        message.setId("exec_action");
        message.add("view", pagectx.getViewData());
        message.setSessionid(sessionid);
        
        for (String name : parameters.keySet())
            message.add(name, parameters.get(name));
        
        return (ViewData)Service.callServer(
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
     * @param resp
     * @param view
     */
    private final void configResponse(HttpServletResponse resp, ViewData view) {
        String contenttype = view.getContentType();
        
        resp.setContentType((contenttype == null)? "text/html" : contenttype);
        resp.setCharacterEncoding("utf-8");
        
        for (String key : view.getHeaderKeys())
            resp.setHeader(key, view.getHeader(key));
    }
    
    /**
     * 
     * @param sessionid
     * @param appname
     * @param pagename
     * @param logid
     * @return
     */
    private final PageContext createPageContext(String sessionid,
            String appname, String pagename, int logid) throws Exception {
        AppContext appctx;
        PageContext pagectx;
        List<SessionContext> sessions;
        SessionContext sessionctx;
        
        if (!apps.containsKey(sessionid)) {
            sessions = new ArrayList<SessionContext>();
            apps.put(sessionid, sessions);
            
            sessionctx = new SessionContext();
            sessions.add(sessionctx);
        } else {
            sessions = apps.get(sessionid);
            
            if (logid >= sessions.size()) {
                sessionctx = new SessionContext();
                sessions.add(sessionctx);
            } else {
                sessionctx = sessions.get(logid);
            }
        }
        
        appctx = (sessionctx.contains(appname))?
                sessionctx.getAppContext(appname) : new AppContext(appname);
                
        pagectx = new PageContext(pagename);
        pagectx.setAppContext(appctx);
        pagectx.setLogid(logid);
        pagectx.setLocale(new Iocaste(this).getLocale());
        
        appctx.put(pagename, pagectx);
        sessionctx.put(appname, appctx);
        
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
        int logid = 0;
        PageContext pagectx = null;
        Iocaste iocaste = new Iocaste(this);
        
        if (apps.containsKey(sessionid)) {
            pagectx = getPageContext(req, sessionid);
            logid = apps.get(sessionid).size();
        }
        
        if (pagectx == null) {
            pagectx = createPageContext(sessionid, LOGIN_APP, "authentic",
                    logid);
            renderer.setUsername(NOT_CONNECTED);
        }
        
        if (pagectx.getViewData() != null)
            pagectx = processController(iocaste, req, pagectx);
        
        render(resp, pagectx);
    }

    /**
     * 
     * @param input
     * @param container
     * @throws Exception
     */
    private static final void generateSearchHelp(InputComponent input,
            Container container, Function function) throws Exception {
        SearchHelp sh;
        SHLib shlib = new SHLib(function);
        String shname = input.getModelItem().getSearchHelp();
        ExtendedObject[] shdata = shlib.get(shname);
        
        if (shdata == null || shdata.length == 0)
            return;
        
        sh = new SearchHelp(container, input.getName()+".sh");
        sh.setHtmlName(input.getHtmlName()+".sh");
        sh.setModelName((String)shdata[0].getValue("MODEL"));
        sh.setExport((String)shdata[0].getValue("EXPORT"));
        
        for (int i = 1; i < shdata.length; i++)
            sh.addModelItemName((String)shdata[i].getValue("ITEM"));
        
        input.setSearchHelp(sh);
    }
    
    /**
     * 
     * @param pagetrack
     * @return
     */
    private final int getLogid(String pagetrack) {
        String[] parsed = pagetrack.split(":");
        
        return Integer.parseInt(parsed[1]);
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
        int t, logid;
        PageContext pagectx;
        List<FileItem> files = null;
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
                
                pagetrack = fileitem.getString();
                
                break;
            }
        } else {
            pagetrack = req.getParameter("pagetrack");
        }
        
        if (pagetrack == null)
            return null;
        
        pageparse = pagetrack.split(":");
        logid = Integer.parseInt(pageparse[1]);
        
        pagetrack = pageparse[0];
        pageparse = pagetrack.split("\\.");
        
        t = pageparse.length - 1;
        
        for (int i = 0; i < t; i++)
            if (i > 0)
                pageparse[0] += ("." + pageparse[i]);
        
        pageparse[1] = pageparse[t];
        
        pagectx = getPageContext(sessionid, pageparse[0], pageparse[1], logid);
        
        if (pagectx == null)
            return null;
        
        pagectx.setFiles(files);
        
        return pagectx;
    }
    
    /**
     * 
     * @param sessionid
     * @param appname
     * @param pagename
     * @param logid
     * @return
     */
    private final PageContext getPageContext (String sessionid, String appname,
            String pagename, int logid) {
        AppContext appctx;
        List<SessionContext> sessions = apps.get(sessionid);
        
        if (logid >= sessions.size())
            return null;
        
        appctx = sessions.get(logid).getAppContext(appname);
        
        return (appctx == null)? null : appctx.getPageContext(pagename);
    }
    
    /**
     * 
     * @param sessionid
     * @param appname
     * @param pagename
     * @param logid
     * @return
     */
    public static final ViewData getView(String sessionid, String appname,
            String pagename, int logid) {
        AppContext appcontext = apps.get(sessionid).
                get(logid).getAppContext(appname);
        
        return appcontext.getPageContext(pagename).getViewData();
    }
    
    /**
     * 
     * @param sessionid
     * @param logid
     * @return
     */
    public static final String[] popPage(String sessionid, int logid) {
        return apps.get(sessionid).get(logid).popPage();
    }
    
    /**
     * 
     * @param iocaste
     * @param req
     * @param pagepos
     * @return
     * @throws Exception
     */
    private final PageContext processController(Iocaste iocaste,
            HttpServletRequest req, PageContext pagectx) throws Exception {
        InputData inputdata;
        ControlComponent action;
        Enumeration<?> parameternames;
        PageContext pagectx_;
        Map<String, String[]> parameters;
        ViewData view;
        String  appname, pagename, key, pagetrack = null, actionname = null;
        
        if (ServletFileUpload.isMultipartContent(req)) {
            parameters = processMultipartContent(req, pagectx);
        } else {
            parameters = new HashMap<String, String[]>();
            
            parameternames = req.getParameterNames();
            
            while (parameternames.hasMoreElements()) {
                key = (String)parameternames.nextElement();
                
                if (pagectx.isAction(key)) {
                    actionname = req.getParameterValues(key)[0];
                    parameters.put("action", req.getParameterValues(key));
                } else {
                    parameters.put(key, req.getParameterValues(key));
                }
            }
            
            if (parameters.size() == 0)
                return pagectx;
        }

        if (parameters.containsKey("pagetrack")) {
            pagetrack = parameters.get("pagetrack")[0];
            parameters.remove("pagetrack");
        }
        
        view = callController(sessionid, parameters, pagectx);
        
        action = view.getElement(actionname);
        if (view.hasPageCall() && (action == null ||
                !action.isCancellable() || action.allowStacking()))
            pushPage(sessionid, view.getAppName(), view.getPageName(),
                    view.getLogid());
        
        view.clearInputs();
        
        inputdata = new InputData();
        inputdata.view = view;
        inputdata.container = null;
        inputdata.function = this;
        
        for (Container container : view.getContainers()) {
            inputdata.element = container;
            registerInputs(inputdata);
        }
        
        updateView(sessionid, view, this);
        
        renderer.setMessageText(view.getTranslatedMessage());
        renderer.setMessageType(view.getMessageType());
        renderer.setUsername((iocaste.isConnected())?
                iocaste.getUsername():NOT_CONNECTED);
        
        appname = view.getRedirectedApp();
        if (appname == null)
            appname = pagectx.getAppContext().getName();
        
        pagename = view.getRedirectedPage();
        if (pagename == null)
            pagename = pagectx.getName();
        
        pagectx_ = getPageContext(sessionid, appname, pagename,
                getLogid(pagetrack));
        
        if (pagectx_ == null)
            pagectx_ = createPageContext(sessionid, appname, pagename,
                    getLogid(pagetrack));
        
        pagectx_.setReloadableView(view.isReloadableView());
        
        pagectx_.clearParameters();
        for (String name : view.getExportable())
            pagectx_.addParameter(name, view.getParameter(name));
        
        return pagectx_;
    }
    
    /**
     * 
     * @param req
     * @param pagectx
     * @throws Exception
     */
    private final Map<String, String[]> processMultipartContent(
            HttpServletRequest req, PageContext pagectx) throws Exception {
        String filename, fieldname;
        MultipartElement[] elements;
        FileItem fileitem;
        Map<String, String[]> parameters;
        
        parameters = new HashMap<String, String[]>();
        elements = pagectx.getViewData().getMultipartElements();
        
        for (MultipartElement element : elements) {
            for (Object object : pagectx.getFiles()) {
            	fileitem = (FileItem)object;
                fieldname = fileitem.getFieldName();
                
                if (fileitem.isFormField()) {
                    if (pagectx.isAction(fieldname))
                        fieldname = "action";
                    
                    parameters.put(fieldname,
                    		new String[] {fileitem.getString()});
                    
                    continue;
                }
                
                if (!fieldname.equals(element.getHtmlName()))
                    continue;
                
                filename = fileitem.getName();
                
                try {
                    if (filename.equals("")) {
                        element.setError(MultipartElement.EMPTY_FILE_NAME);
                    } else {
                        fileitem.write(new File(
                                element.getDestiny(), filename));
                        element.setError(0);
                    }
                } catch (FileNotFoundException e) {
                    element.setError(MultipartElement.FILE_NOT_FOUND);
                }
                
                parameters.put(fieldname, new String[] {filename});
            }
        }
        
        return parameters;
    }
    
    /**
     * 
     * @param sessionid
     * @param appname
     * @param pagename
     * @param logid
     */
    public static final void pushPage(String sessionid, String appname,
            String pagename, int logid) {
        apps.get(sessionid).get(logid).pushPage(appname, pagename);
    }
    
    /**
     * 
     * @param inputdata
     * @throws Exception
     */
    private static final void registerInputs(InputData inputdata)
            throws Exception {
        InputData inputdata_;
        Container container;
        InputComponent input;
        DocumentModelItem modelitem;
        
        if (inputdata.element == null)
            return;
        
        inputdata.element.setView(inputdata.view);
        
        if (inputdata.element.isContainable()) {
            container = (Container)inputdata.element;
            
            inputdata_ = new InputData();
            inputdata_.container = container;
            inputdata_.view = inputdata.view;
            inputdata_.function = inputdata.function;
            
            for (Element element : container.getElements()) {
                inputdata_.element = element;
                registerInputs(inputdata_);
            }
            
            return;
        }
        
        if (inputdata.element.isDataStorable()) {
            inputdata.view.addInput(inputdata.element.getHtmlName());
            
            input = (InputComponent)inputdata.element;            
            modelitem = input.getModelItem();
            
            if (input.getSearchHelp() == null && modelitem != null &&
                    modelitem.getSearchHelp() != null)
                generateSearchHelp(input, inputdata.container,
                        inputdata.function);
        }
        
        if (inputdata.element.hasMultipartSupport())
            inputdata.view.addMultipartElement(
                    (MultipartElement)inputdata.element);
    }
    
    /**
     * 
     * @param resp
     * @param pagectx
     * @throws Exception
     */
    private final void render(HttpServletResponse resp, PageContext pagectx)
            throws Exception {
        InputData inputdata;
        OutputStream os;
        String[] text;
        AppContext appctx;
        ViewData viewdata;
        Message message = new Message();
        PrintWriter writer = resp.getWriter();

        viewdata = pagectx.getViewData();
        
        if (viewdata == null || pagectx.isReloadableView()) {
            appctx = pagectx.getAppContext();
            
            message.setId("get_view_data");
            message.add("app", appctx.getName());
            message.add("page", pagectx.getName());
            message.add("parameters", pagectx.getParameters());
            message.add("logid", pagectx.getLogid());
            message.add("locale", pagectx.getLocale());
            message.setSessionid(sessionid);
            
            viewdata = (ViewData)Service.callServer(
                    composeUrl(appctx.getName()), message);
            
            inputdata = new InputData();
            inputdata.view = viewdata;
            inputdata.container = null;
            inputdata.function = this;
            
            for (Container container : viewdata.getContainers()) {
                inputdata.element = container;
                registerInputs(inputdata);
            }
            
            pagectx.setViewData(viewdata);
        }
        
        /*
         * reseta o servlet response para que possamos
         * usar OutputStream novamente (já é utilizado em
         * Service.callServer()).
         */
        if (viewdata.getContentType() != null) {
            resp.reset();
            
            configResponse(resp, viewdata);
            
            os = resp.getOutputStream();
            
            for (String line : viewdata.getPrintLines())
                os.write(line.getBytes());
            
            os.flush();
            os.close();
            
            return;
        }
        
        configResponse(resp, viewdata);
        
        if (style == null)
            style = Style.get("DEFAULT", this);
        
        renderer.setCssElements(style);
        renderer.setLogid(pagectx.getLogid());
        text = renderer.run(pagectx.getViewData());

        pagectx.setActions(renderer.getActions());
        
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
    
    /**
     * 
     * @param sessionid
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void updateView(String sessionid, ViewData view,
            Function function) throws Exception {
        AppContext appcontext = apps.get(sessionid).get(view.getLogid()).
                getAppContext(view.getAppName());
        InputData inputdata = new InputData();
        
        inputdata.view = view;
        inputdata.container = null;
        inputdata.function = function;
        
        for (Container container : view.getContainers()) {
            inputdata.element = container;
            registerInputs(inputdata);
        }
        
        appcontext.getPageContext(view.getPageName()).setViewData(view);
    }
}

class InputData {
    public ViewData view;
    public Element element;
    public Container container;
    public Function function;
}