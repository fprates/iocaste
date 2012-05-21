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
import org.iocaste.protocol.user.Authorization;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.MultipartElement;
import org.iocaste.shell.common.SHLib;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class PageRenderer extends HttpServlet implements Function {
    private static final long serialVersionUID = -8143025594178489781L;
    private static final String LOGIN_APP = "iocaste-login";
    private static final String NOT_CONNECTED = "not.connected";
    private static final byte AUTHORIZATION_ERROR = 1;
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
        message.setSessionid(getComplexId(sessionid, pagectx.getLogid()));
        
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
        resp.setCharacterEncoding("UTF-8");
        
        for (String key : view.getHeaderKeys())
            resp.setHeader(key, view.getHeader(key));
    }
    
    /**
     * 
     * @param contextdata
     * @return
     * @throws Exception
     */
    private final PageContext createPageContext(ContextData contextdata)
            throws Exception {
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
            
            if (contextdata.logid >= sessions.size()) {
                sessionctx = new SessionContext();
                sessions.add(sessionctx);
            } else {
                sessionctx = sessions.get(contextdata.logid);
            }
        }
        
        appctx = (sessionctx.contains(contextdata.appname))?
                sessionctx.getAppContext(contextdata.appname) :
                    new AppContext(contextdata.appname);
                
        pagectx = new PageContext(contextdata.pagename);
        pagectx.setAppContext(appctx);
        pagectx.setLogid(contextdata.logid);
        
        appctx.put(contextdata.pagename, pagectx);
        sessionctx.put(contextdata.appname, appctx);
        
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
        Iocaste iocaste;
        ContextData contextdata;
        int logid = 0;
        PageContext pagectx = null;
        
        req.setCharacterEncoding("UTF-8");
        
        if (apps.containsKey(sessionid)) {
            pagectx = getPageContext(req, sessionid);
            logid = apps.get(sessionid).size();
        }
        
        if (pagectx == null) {
            contextdata = new ContextData();
            contextdata.sessionid = sessionid;
            contextdata.appname = LOGIN_APP;
            contextdata.pagename = "authentic";
            contextdata.logid = logid;
            
            pagectx = createPageContext(contextdata);
        }
        
        if (pagectx.getViewData() != null) {
            iocaste = new Iocaste(this);
            pagectx = processController(iocaste, req, pagectx);
        }
        
        render(resp, pagectx);
    }

    /**
     * 
     * @param input
     * @param inputdata
     * @throws Exception
     */
    private static final void generateSearchHelp(InputComponent input,
            InputData inputdata) throws Exception {
        SearchHelp sh;
        SHLib shlib = new SHLib(inputdata.function);
        String shname = input.getModelItem().getSearchHelp();
        ExtendedObject[] shdata = shlib.get(shname);
        
        if (shdata == null || shdata.length == 0)
            return;
        
        sh = new SearchHelp(inputdata.container, input.getName()+".sh");
        sh.setHtmlName(input.getHtmlName()+".sh");
        sh.setModelName((String)shdata[0].getValue("MODEL"));
        sh.setExport((String)shdata[0].getValue("EXPORT"));
        
        for (int i = 1; i < shdata.length; i++) {
            shname = shdata[i].getValue("ITEM");
            sh.addModelItemName(shname);
        }
        
        input.setSearchHelp(sh);
    }
    
    /**
     * 
     * @param sessionid
     * @param logid
     * @return
     */
    private final String getComplexId(String sessionid, int logid) {
        return new StringBuilder(sessionid).append(":").append(logid).
                toString();
    }
    
    /**
     * 
     * @param pagetrack
     * @return
     */
    private static final int getLogid(String pagetrack) {
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
     * @param container
     * @return
     */
    private static final Element[] getMultiLineElements(Container container) {
        byte selectiontype;
        Element element;
        SearchHelp sh;
        Table table;
        TableColumn[] columns;
        List<Element> elements = new ArrayList<Element>();
        String name, linename, htmlname, markname = null;
        int i = 0;
        
        if (container.getType() != Const.TABLE)
            new RuntimeException("Multi-line container not supported.");
        
        table = (Table)container;
        name = table.getName();
        selectiontype = table.getSelectionType();
        
        if (selectiontype == Table.SINGLE)
            markname = new StringBuilder(name).append(".mark").toString();
        
        columns = table.getColumns();
        elements = new ArrayList<Element>();
        
        for (TableItem item : table.getItens()) {
            linename = new StringBuilder(name).append(".").append(i++).
                    append(".").toString();
            
            for (TableColumn column: columns) {
                element = (column.isMark())?
                        item.get("mark") : item.get(column.getName());
                
                if (element == null)
                    continue;
                
                if (column.isMark() && markname != null)
                    htmlname = markname;
                else
                    htmlname = new StringBuilder(linename).
                            append(element.getName()).toString();
                
                element.setHtmlName(htmlname);
                elements.add(element);
                
                /*
                 * ajusta nome de ajuda de pesquisa, se houver
                 */
                if (!element.isDataStorable())
                    continue;
                
                sh = ((InputComponent)element).getSearchHelp();
                
                if (sh == null)
                    continue;
                
                htmlname = new StringBuilder(linename).
                        append(sh.getName()).toString();
                
                sh.setHtmlName(htmlname);
                elements.add(sh);
            }
        }
        
        return elements.toArray(new Element[0]);
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
        ContextData contextdata;
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
        
        for (int i = 1; i < t; i++)
            pageparse[0] += ("." + pageparse[i]);
        
        pageparse[1] = pageparse[t];
        
        contextdata = new ContextData();
        contextdata.sessionid = sessionid;
        contextdata.appname = pageparse[0];
        contextdata.pagename = pageparse[1];
        contextdata.logid = logid;
        
        pagectx = getPageContext(contextdata);
        
        if (pagectx == null)
            return null;
        
        pagectx.setFiles(files);
        
        return pagectx;
    }
    
    /**
     * 
     * @param contextdata
     * @return
     */
    private final PageContext getPageContext (ContextData contextdata) {
        AppContext appctx;
        List<SessionContext> sessions = apps.get(sessionid);
        
        if (contextdata.logid >= sessions.size())
            return null;
        
        appctx = sessions.get(contextdata.logid).getAppContext(
                contextdata.appname);
        
        return (appctx == null)? null : appctx.getPageContext(
                contextdata.pagename);
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
            String pagename) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        AppContext appcontext = apps.get(complexid[0]).get(logid).
                getAppContext(appname);
        
        return appcontext.getPageContext(pagename).getViewData();
    }
    
    /**
     * 
     * @param sessionid
     * @param logid
     * @return
     */
    public static final String[] popPage(String sessionid) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return apps.get(complexid[0]).get(logid).popPage();
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
        int logid;
        ContextData contextdata;
        InputData inputdata;
        ControlComponent action;
        Enumeration<?> parameternames;
        PageContext pagectx_;
        Map<String, String[]> parameters;
        ViewData view;
        Authorization authorization;
        String complexid, appname, pagename, key, pagetrack = null,
                actionname = null;
        
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
        logid = getLogid(pagetrack);
        complexid = getComplexId(sessionid, logid);
        if (view.hasPageCall() && (action == null ||
                !action.isCancellable() || action.allowStacking()))
            pushPage(complexid, view.getAppName(),
                    view.getPageName());
        
        view.clearInputs();
        
        inputdata = new InputData();
        inputdata.view = view;
        inputdata.container = null;
        inputdata.function = this;
        
        for (Container container : view.getContainers()) {
            inputdata.element = container;
            registerInputs(inputdata);
        }
        
        updateView(getComplexId(sessionid, logid), view, this);
        
        appname = view.getRedirectedApp();
        if (appname == null)
            appname = pagectx.getAppContext().getName();
        
        pagename = view.getRedirectedPage();
        if (pagename == null)
            pagename = pagectx.getName();
        
        authorization = new Authorization();
        authorization.setName("APPLICATION.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", appname);
        
        if (!iocaste.isAuthorized(authorization)) {
            pagectx.setError(AUTHORIZATION_ERROR);
            pagectx.getViewData().message(Const.ERROR, "user.not.authorized");
            
            return pagectx;
        } else {
            pagectx.setError((byte)0);
        }
        
        contextdata = new ContextData();
        contextdata.sessionid = sessionid;
        contextdata.appname = appname;
        contextdata.pagename = pagename;
        contextdata.logid = logid;
        
        pagectx_ = getPageContext(contextdata);
        if (pagectx_ == null)
            pagectx_ = createPageContext(contextdata);
        
        pagectx_.setReloadableView(view.isReloadableView());
        pagectx_.clearParameters();
        for (String name : view.getExportable())
            pagectx_.addParameter(name, view.getParameter(name));
        
        if (view.getAppName().equals(LOGIN_APP) && iocaste.isConnected())
            pagectx_.setUsername((String)view.getParameter("username"));
        else
            pagectx_.setUsername(pagectx.getUsername());
        
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
            String pagename) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        apps.get(complexid[0]).get(logid).pushPage(appname, pagename);
    }
    
    /**
     * 
     * @param inputdata
     * @throws Exception
     */
    private static final void registerInputs(InputData inputdata)
            throws Exception {
        Element[] elements;
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
            
            elements = (container.isMultiLine())?
                    getMultiLineElements(container) : container.getElements();
                    
            for (Element element : elements) {
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
                generateSearchHelp(input, inputdata);
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
        String username;
        int logid;
        InputData inputdata;
        OutputStream os;
        String[] text;
        AppContext appctx;
        ViewData viewdata;
        PrintWriter writer;
        Message message = new Message();

        viewdata = pagectx.getViewData();
        
        if (pagectx.getError() == 0 &&
                (viewdata == null || pagectx.isReloadableView())) {
            appctx = pagectx.getAppContext();
            logid = pagectx.getLogid();
            
            message.setId("get_view_data");
            message.add("app", appctx.getName());
            message.add("page", pagectx.getName());
            message.add("parameters", pagectx.getParameters());
            message.setSessionid(getComplexId(sessionid, logid));
            
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

        username = pagectx.getUsername();
        renderer.setMessageText(viewdata.getTranslatedMessage());
        renderer.setMessageType(viewdata.getMessageType());
        renderer.setUsername((username == null)? NOT_CONNECTED : username);
        renderer.setCssElements(style);
        renderer.setLogid(pagectx.getLogid());
        text = renderer.run(pagectx.getViewData());

        pagectx.setActions(renderer.getActions());
        
        writer = resp.getWriter();
        
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
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        AppContext appcontext = apps.get(complexid[0]).get(logid).
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

class ContextData {
    public String sessionid;
    public String appname;
    public String pagename;
    public int logid;
}