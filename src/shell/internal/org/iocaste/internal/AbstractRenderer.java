package org.iocaste.internal;

import java.io.BufferedOutputStream;
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
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Handler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.StandardService;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.MultipartElement;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.View;

public abstract class AbstractRenderer extends HttpServlet implements Function {
    private static final String NOT_CONNECTED = "not.connected";
    private static final long serialVersionUID = -7711799346205632679L;
    private static final boolean NEW_SESSION = false;
    private static final boolean KEEP_SESSION = true;
    private static final String STD_CONTENT = "text/html";
    private static final byte AUTHORIZATION_ERROR = 1;
    private String jsessionid, servername;
    public static Map<String, List<SessionContext>> apps;
    protected String hostname, protocol;
    protected int port;

    static {
        apps = new HashMap<>();
    }
    
    protected abstract void callController(ControllerData config)
            throws Exception;
    
    /**
     * 
     * @param app
     * @return
     */
    protected final String composeUrl(String app) {
        return new StringBuffer(getServerName()).append("/").
                append(app).append("/view.html").toString();
    }
    
    /**
     * 
     * @param resp
     * @param view
     */
    private final void configResponse(HttpServletResponse resp,
            PageContext pagectx) {
        String contenttype = pagectx.getContentType();
        
        resp.setContentType((contenttype == null)? STD_CONTENT : contenttype);
        resp.setCharacterEncoding("UTF-8");
        
        if (pagectx.headervalues == null)
            return;
        
        for (String key : pagectx.headervalues.keySet())
            resp.setHeader(key, pagectx.headervalues.get(key));
    }
    
    /**
     * 
     * @param contextdata
     * @return
     */
    protected final PageContext createPageContext(ContextData contextdata) {
        AppContext appctx;
        PageContext pagectx;
        List<SessionContext> sessions;
        SessionContext sessionctx;
        
        if (!apps.containsKey(contextdata.sessionid)) {
            sessions = new ArrayList<>();
            apps.put(contextdata.sessionid, sessions);
            
            sessionctx = new SessionContext();
            sessions.add(sessionctx);
        } else {
            sessions = apps.get(contextdata.sessionid);
            if (contextdata.logid >= sessions.size()) {
                sessionctx = new SessionContext();
                sessions.add(sessionctx);
            } else {
                sessionctx = sessions.get(contextdata.logid);
            }
        }
        
        if (sessionctx.contains(contextdata.appname))
            appctx = sessionctx.getAppContext(contextdata.appname);
        else
            appctx = new AppContext(contextdata.appname);
        
        pagectx = new PageContext(contextdata.pagename);
        pagectx.setAppContext(appctx);
        pagectx.setLogid(contextdata.logid);
        pagectx.setInitialize(contextdata.initialize);
        appctx.put(contextdata.pagename, pagectx);
        sessionctx.put(contextdata.appname, appctx);
        
        return pagectx;
    }
    
    /**
     * 
     * @param sessionid
     * @param pagectx
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected View createView(String sessionid, PageContext pagectx)
            throws Exception {
        Object[] viewreturn;
        String complexid, appname, pagename, csslink;
        int logid;
        Input input;
        Message message;
        Map<String, Object> parameters;
        AppContext appctx;
        View view;
        Service service;
        StyleSheet stylesheet;
        HeaderLink link;
        
        appctx = pagectx.getAppContext();
        appname = appctx.getName();
        pagename = pagectx.getName();
        logid = pagectx.getLogid();
        complexid = getComplexId(sessionid, logid);
        
        if (appname == null || pagename == null)
            throw new IocasteException("page not especified.");
        
        view = pagectx.getViewData(); 
        if (!pagectx.keepView() || view == null)
            view = new View(appname, pagename);
        
        stylesheet = DefaultStyle.instance();
        view.importStyle(stylesheet);
        
        message = new Message("get_view_data");
        message.add("view", view);
        message.add("init", pagectx.isInitializableView());
        message.add("parameters", pagectx.parameters);
        message.add("servername", hostname);
        message.add("protocol", protocol);
        message.add("port", port);
        message.setSessionid(complexid);
        
        if (pagectx.initparams != null) {
            parameters = new HashMap<>();
            for (String name : pagectx.initparams)
                parameters.put(name, pagectx.parameters.get(name));
            pagectx.initparams = null;
            pagectx.parameters = parameters;
        }
        
        try {
            service = new StandardService(complexid, composeUrl(appname));
            viewreturn = (Object[])service.call(message);
            
            view = (View)viewreturn[0];
            pagectx.headervalues = (Map<String, String>)viewreturn[1];
            pagectx.setContentType((String)viewreturn[2]);
            pagectx.setContentEncoding((String)viewreturn[3]);
            
            input = new Input();
            input.view = view;
            input.container = null;
            input.function = this;
            input.pagectx = pagectx;
            input.pagectx.inputs.clear();
            input.pagectx.mpelements.clear();
            
            /*
             * deixa registerInputs() antes do commit(),
             * para que a conexão seja encerrada.
             */
            for (Container container : view.getContainers()) {
                input.element = container;
                input.register();
            }
            
            Common.commit(getServerName(), complexid);
            new Iocaste(this).commit();
        } catch (Exception e) {
            Common.rollback(getServerName(), complexid);
            new Iocaste(this).rollback();
            throw e;
        }
        
        csslink = stylesheet.getLink();
        if (csslink != null) {
            link = new HeaderLink("stylesheet", "text/css", csslink);
            view.add(link);
        }
        
        return view;
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        jsessionid = req.getSession().getId();
        servername = new StringBuffer(req.getScheme()).append("://127.0.0.1:").
                append(req.getLocalPort()).toString();
        
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
        jsessionid = req.getSession().getId();
        servername = new StringBuffer(req.getScheme()).append("://127.0.0.1:").
                append(req.getLocalPort()).toString();
        
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
     * @param appname
     * @param complexid
     */
    private final void execute(String appname, String complexid) {
        String url;
        Service service;
        Message message = new Message("set_current_app");
        message.add("current_app", appname);
        message.setSessionid(complexid);
        
        url = new StringBuilder(getServerName()).append(Iocaste.SERVERNAME).
                toString();
        service = new StandardService(complexid, url);
        service.call(message);
    }

    @Override
    public final <T extends Handler> T get(String handler) {
        return null;
    }
    
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
     * @param contextdata
     * @return
     */
    protected final PageContext getPageContext(ContextData contextdata) {
        AppContext appctx;
        List<SessionContext> sessions = apps.get(contextdata.sessionid);
        
        if ((sessions == null) || (contextdata.logid >= sessions.size()))
            return null;
        
        appctx = sessions.get(contextdata.logid).getAppContext(
                contextdata.appname);
        if (appctx == null)
            return null;
        
        return appctx.getPageContext(contextdata.pagename);
    }
    
    /**
     * 
     * @param req
     * @param sessionctx
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final PageContext getPageContext(HttpServletRequest req)
            throws Exception {
        PageStackItem pagestackitem;
        ContextData contextdata;
        String[] pageparse;
        ServletFileUpload fileupload;
        int t, logid;
        long sequence;
        PageContext pagectx;
        List<FileItem> files = null;
        String pagetrack = null, sessionid = req.getSession().getId();
        
        /*
         * Obtem rastreamento da sessão
         */
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
        
        /*
         * processa rastreamento da sessão e obtem contexto da página
         */
        if (pagetrack == null)
            return null;
        
        pageparse = pagetrack.split(":");
        if (!sessionid.equals(pageparse[1]))
            return null;

        pagetrack = pageparse[0];
        logid = Integer.parseInt(pageparse[2]);
        sequence = Long.parseLong(pageparse[3]);
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
        
        /*
         * Valida sequenciamento de telas
         */
        pagectx.setFiles(files);
        if (sequence != pagectx.getSequence()) {
            pagestackitem = home(
                    getComplexId(contextdata.sessionid, logid), null);
            if (pagestackitem != null) {
                contextdata.appname = pagestackitem.getApp();
                contextdata.pagename = pagestackitem.getPage();
                pagectx = getPageContext(contextdata);
                pagectx.setViewData(null);
            }
        }
        
        pagectx.setSequence(sequence);
        return pagectx;
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
     * @param complexid
     * @return
     */
    private final String getUsername(ContextData ctxdata) {
        String url;
        Message message;
        Service service;
        String complexid = getComplexId(ctxdata.sessionid, ctxdata.logid);
        
        message = new Message("get_username");
        message.setSessionid(complexid);
        
        url = new StringBuilder(getServerName()).append(Iocaste.SERVERNAME).
                toString();
        service = new StandardService(complexid, url);
        return (String)service.call(message);
    }
    
    /**
     * 
     * @param getSessionId()
     * @return
     */
    public static final PageStackItem home(String sessionid, String page) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return apps.get(complexid[0]).get(logid).home(page);
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
     * @param ctxdata
     * @return
     */
    protected boolean isConnected(ContextData ctxdata) {
        String url;
        Message message;
        Service service;
        String complexid = getComplexId(ctxdata.sessionid, ctxdata.logid);
        
        message = new Message("is_connected");
        message.setSessionid(complexid);
        
        url = new StringBuilder(getServerName()).append(Iocaste.SERVERNAME).
                toString();
        service = new StandardService(complexid, url);
        return (boolean)service.call(message);
    }
    
    /**
     * 
     * @param appname
     * @param complexid
     * @return
     */
    protected boolean isExecuteAuthorized(String appname, String complexid) {
        String url;
        Message message;
        Service service;
        Authorization authorization = new Authorization("APPLICATION.EXECUTE");
        
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", appname);
        
        message = new Message("is_authorized");
        message.add("authorization", authorization);
        message.setSessionid(complexid);
        
        url = new StringBuilder(getServerName()).append(Iocaste.SERVERNAME).
                toString();
        service = new StandardService(complexid, url);
        return (Boolean)service.call(message);
    }
    
    /**
     * 
     * @param req
     * @param pagectx
     * @param sessionid
     * @return
     * @throws Exception
     */
    protected final PageContext processController(HttpServletRequest req,
            PageContext pagectx, String sessionid) throws Exception {
        long sequence;
        ControllerData config;
        ContextData contextdata;
        ControlComponent action;
        Enumeration<?> parameternames;
        PageContext pagectx_;
        Map<String, String[]> parameters;
        String appname, pagename, key, pagetrack = null, actionname = null;
        
        /*
         * obtem parâmetros da requisição
         */
        if (ServletFileUpload.isMultipartContent(req)) {
            parameters = processMultipartContent(req, pagectx);
            actionname = parameters.get("action")[0];
        } else {
            parameters = new HashMap<>();
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

        /*
         * prepara contexto e chama controlador
         */
        if (parameters.containsKey("pagetrack")) {
            pagetrack = parameters.get("pagetrack")[0];
            parameters.remove("pagetrack");
        }
        
        config = new ControllerData();
        config.state.view = pagectx.getViewData();
        config.values = parameters;
        config.function = this;
        config.contextname = pagectx.getAppContext().getName();
        config.logid = getLogid(pagetrack);
        config.sessionid = getComplexId(sessionid, config.logid);
        config.servername = getServerName();
        config.pagectx = pagectx;
        
        if (parameters.containsKey("event"))
            config.execonevent(parameters);
        callController(config);
        
        /*
         * processa atualização na visão após chamada do controlador
         */
        if ((actionname == null) || (actionname.length() == 0))
            action = null;
        else
            action = config.state.view.getElement(actionname);
        
        if (config.state.pagecall && (action == null ||
                !action.isCancellable() || action.allowStacking()))
            pushPage(config.sessionid, config.state.view);
        
        config.pagectx.inputs.clear();
        updateView(config.sessionid, config.state.view, this);
        
        /*
         * prepara retorno para resposta, seja na visão atual ou se for
         * redirecionado
         */
        appname = config.state.rapp;
        if (appname == null)
            appname = pagectx.getAppContext().getName();
        
        pagename = config.state.rpage;
        if (pagename == null)
            pagename = pagectx.getName();
        
        /*
         * testa autorização para execução e sequencia de telas
         */
        if (!isExecuteAuthorized(appname, config.sessionid) &&
                (config.state.rapp != null) &&
                        !config.state.rapp.equals("iocaste-login")) {
            pagectx.setError(AUTHORIZATION_ERROR);
            pagectx.message(Const.ERROR,
                    Controller.messages.get("user.not.authorized"), null);
            
            return pagectx;
        }
        
        pagectx.setContextUrl(config.contexturl);
        pagectx.setError((byte)0);
        sequence = pagectx.getSequence();
        contextdata = new ContextData();
        contextdata.sessionid = sessionid;
        contextdata.appname = appname;
        contextdata.pagename = pagename;
        contextdata.logid = config.logid;
        
        pagectx_ = getPageContext(contextdata);
        if (pagectx_ == null) {
            pagectx_ = createPageContext(contextdata);
            pagectx_.setSequence(sequence);
        }
        
        if (!config.event && !config.state.download)
            sequence++;
        
        pagectx_.setInitialize(config.state.initialize);
        pagectx_.setSequence(sequence);
        pagectx_.setKeepView(config.state.keepview);
        pagectx_.setReloadableView(config.state.reloadable);
        pagectx_.parameters = config.state.parameters;
        pagectx_.initparams = config.state.initparams;
        pagectx_.message(config.state.messagetype, config.state.messagetext,
                config.state.messageargs);
        pagectx_.setPopupControl(config.popupcontrol);
        
        if (isConnected(contextdata)) {
            execute(appname, config.sessionid);
            pagectx_.setUsername(getUsername(contextdata));
        } else {
            pagectx_.setUsername(null);
        }
        
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
        String[] values;
        String filename, fieldname, value;
        FileItem fileitem;
        Map<String, String[]> parameters;
        
        parameters = new HashMap<>();
        for (Object object : pagectx.getFiles()) {
            fileitem = (FileItem)object;
            fieldname = fileitem.getFieldName();
            
            if (fileitem.isFormField()) {
                if (pagectx.isAction(fieldname))
                    fieldname = "action";
                
                value = fileitem.getString("UTF-8");
                values = parameters.get(fieldname);
                if ((values == null) || (values != null && value.length() > 0))
                    parameters.put(fieldname, new String[] {value});
                
                continue;
            }

            for (MultipartElement element : pagectx.mpelements) {
                if (!fieldname.equals(element.getHtmlName()))
                    continue;
                
                filename = fileitem.getName();
                if (filename.equals("")) {
                    element.setError(MultipartElement.EMPTY_FILE_NAME);
                } else {
                    if (element.isEnabled()) {
                        element.set(filename);
                        element.setContent(fileitem.get());
                        element.setError(0);
                    }
                }
                
                parameters.put(fieldname, new String[] {filename});
            }
        }
        
        return parameters;
    }
    
    /**
     * 
     * @param getSessionId()
     * @param appname
     * @param pagename
     * @param logid
     */
    public static final void pushPage(String sessionid, View view) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        List<SessionContext> sessionsctx = apps.get(complexid[0]);
        
        if (sessionsctx == null)
            return;
        
        sessionsctx.get(logid).pushPage(view);
    }
    
    /**
     * 
     * @param renderer
     * @param view
     * @param tracking
     * @throws Exception
     */
    protected final boolean render(HtmlRenderer renderer,
            HttpServletResponse resp, PageContext pagectx,
            TrackingData tracking) throws Exception {
        BufferedOutputStream bos;
        byte[] content;
        OutputStream os;
        List<String> text;
        PrintWriter writer;
        View view = pagectx.getViewData();
        
        if (view == null)
            throw new Exception("Cannot render a null vision.");
        
        /*
         * reseta o servlet response para que possamos
         * usar OutputStream novamente (já é utilizado em
         * Service.callServer()).
         */
        
        if (pagectx.getContentType() != null) {
            resp.reset();
            configResponse(resp, pagectx);
            os = resp.getOutputStream();
            bos = new BufferedOutputStream(os);
            
            content = view.getContent();
            if (content != null) {
                bos.write(content);
                resp.setContentLength(content.length);
                resp.setCharacterEncoding(pagectx.getContentEncoding());
            } else {
                for (String line : view.getPrintLines())
                    bos.write(line.getBytes());
            }
            
            pagectx.setContentType(null);
            pagectx.setContentEncoding(null);
            pagectx.headervalues.clear();
            
            bos.flush();
            bos.close();
            return false;
        }
        
        configResponse(resp, pagectx);
        text = renderer.run(view, tracking);
        writer = resp.getWriter();
        if (text != null)
            for (String line : text)
                writer.println(line);
        
        writer.flush();
        writer.close();
        return true;
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
        
        return new StandardService(jsessionid, url);
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setSessionid(java.lang.String)
     */
    @Override
    public final void setSessionid(String sessionid) { }
    
    /**
     * 
     * @param resp
     * @param pagectx
     * @throws Exception
     */
    protected final void startRender(String sessionid, HttpServletResponse resp,
            PageContext pagectx) throws Exception {
        TrackingData tracking;
        HtmlRenderer renderer;
        String username;
        View view;
        boolean hasrendered;

        /*
         * prepara a visão para renderização
         */
        view = pagectx.getViewData();
        if (pagectx.getError() == 0 &&
                (view == null || pagectx.isReloadableView())) {
            view = createView(sessionid, pagectx);
            pagectx.setViewData(view);
        }
        
        /*
         * ajusta e chama o renderizador
         */
        username = pagectx.getUsername();
        renderer = new HtmlRenderer();
        renderer.setPageContext(pagectx);
        renderer.setUsername((username == null)? NOT_CONNECTED : username);
        renderer.setFunction(this);
        tracking = new TrackingData();
        tracking.logid = pagectx.getLogid();
        tracking.sequence = pagectx.getSequence();
        tracking.sessionid = sessionid;
        tracking.contexturl = pagectx.getContextUrl();
        hasrendered = render(renderer, resp, pagectx, tracking);
        if (hasrendered)
            pagectx.setActions(renderer.getActions());
    }
    
    /**
     * 
     * @param getSessionId()
     * @param view
     * @param function
     */
    public static final void updateView(String sessionid, View view,
            Function function) {
        AppContext appcontext;
        Input input;
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        List<SessionContext> sessionctx = apps.get(complexid[0]);
        
        if (sessionctx == null)
            return;
        
        appcontext = sessionctx.get(logid).getAppContext(view.getAppName());
        input = new Input();
        input.view = view;
        input.container = null;
        input.function = function;
        input.pagectx = appcontext.getPageContext(view.getPageName());
        input.pagectx.inputs.clear();
        input.pagectx.mpelements.clear();
        
        for (Container container : view.getContainers()) {
            input.element = container;
            input.register();
        }
        
        appcontext.getPageContext(view.getPageName()).setViewData(view);
    }

}
