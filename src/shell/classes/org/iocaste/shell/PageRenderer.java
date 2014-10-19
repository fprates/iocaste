package org.iocaste.shell;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.iocaste.internal.AbstractRenderer;
import org.iocaste.internal.Common;
import org.iocaste.internal.Controller;
import org.iocaste.internal.HtmlRenderer;
import org.iocaste.internal.Input;
import org.iocaste.internal.InputStatus;
import org.iocaste.internal.ControllerData;
import org.iocaste.internal.TrackingData;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Handler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.MultipartElement;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.AccessTicket;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class PageRenderer extends AbstractRenderer {
    private static final long serialVersionUID = -8143025594178489781L;
    private static final String NOT_CONNECTED = "not.connected";
    private static final String EXCEPTION_HANDLER = "iocaste-exhandler";
    private static final byte AUTHORIZATION_ERROR = 1;
    private static Map<String, List<SessionContext>> apps;
    private String dbname;
    private Map<String, Map<String, String>> style;
    private MessageSource msgsource;
    private static TicketControl tickets;
    
    static {
        apps = new HashMap<>();
        tickets = new TicketControl();
    }
    
    public PageRenderer() {
        style = null;
        msgsource = Messages.getMessages();
    }
    
    public static final String addTicket(Function function, AccessTicket ticket)
    {
        return tickets.add(ticket, function);
    }
    
    /**
     * 
     * @param config
     * @return
     * @throws Exception
     */
    private final void callController(ControllerData config) throws Exception {
        InputStatus status;
        Message message;
        ControlComponent control;
        Service service;
        
        status = Controller.validate(config);
        if (status.fatal != null)
            throw new IocasteException(status.fatal);
        
        config.event = status.event;
        if (status.error > 0 || status.event)
            return;
        
        message = new Message("exec_action");
        message.add("view", config.state.view);
        message.setSessionid(config.sessionid);
        
        for (String name : config.values.keySet())
            message.add(name, config.values.get(name));

        control = config.state.view.getElement(message.getString("action"));
        if (control != null && control.getType() == Const.SEARCH_HELP) {
            config.shcontrol = control;
            config.contexturl = composeUrl("iocaste-search-help");
        } else {
            try {
                service = new Service(config.sessionid,
                        composeUrl(config.contextname));
                config.state = (ViewState)service.call(message);
                
                if (config.state.view.getMessageType() == Const.ERROR)
                    Common.rollback(getServerName(), config.sessionid);
                else
                    Common.commit(getServerName(), config.sessionid);
            } catch (Exception e) {
                Common.rollback(getServerName(), config.sessionid);
                throw e;
            }
        }
    }
    
    /**
     * 
     * @param expagectx
     * @param exception
     * @return
     * @throws Exception
     */
    private final PageContext createExceptionContext(String sessionid,
            PageContext expagectx, Exception exception) throws Exception {
        PageContext pagectx;
        ContextData contextdata = new ContextData();
        
        contextdata.sessionid = sessionid;
        contextdata.appname = EXCEPTION_HANDLER;
        contextdata.pagename = "main";
        contextdata.logid = expagectx.getLogid();
        contextdata.initialize = true;
        
        pagectx = createPageContext(contextdata);
        pagectx.addParameter("exception", exception);
        pagectx.addParameter("exview", expagectx.getViewData());
        pagectx.setUsername(expagectx.getUsername());
        
        return pagectx;
    }
    
    /**
     * Cria uma página de context para conexão.
     * @param sessionid
     * @param logid
     * @return
     */
    private final PageContext createLoginContext(HttpServletRequest req,
            String sessionid, int logid) {
        ContextData contextdata = new ContextData();
        String login = req.getParameter("login-manager");
        
        contextdata.appname = (login == null)? "iocaste-login" : login;
        contextdata.sessionid = sessionid;
        contextdata.pagename = "authentic";
        contextdata.logid = logid;
        contextdata.initialize = true;
        
        return createPageContext(contextdata);
    }
    
    /**
     * 
     * @param sessionid
     * @param pagectx
     * @return
     * @throws Exception
     */
    private final View createView(String sessionid, PageContext pagectx)
            throws Exception {
        String complexid, appname, pagename;
        int logid;
        Input input;
        Message message;
        Map<String, Object> iparams, parameters;
        String[] initparams;
        AppContext appctx;
        View view;
        Service service;
        
        appctx = pagectx.getAppContext();
        appname = appctx.getName();
        pagename = pagectx.getName();
        logid = pagectx.getLogid();
        complexid = getComplexId(sessionid, logid);
        
        if (appname == null || pagename == null)
            throw new IocasteException("page not especified.");
        
        if (appctx.getStyleSheet() == null) {
            if (style == null)
                style = Style.get("DEFAULT", this);
            appctx.setStyleSheet(style);
        }
        
        view = pagectx.getViewData(); 
        if (!pagectx.keepView() || view == null)
            view = new View(appname, pagename);
        
        view.setStyleSheet(appctx.getStyleSheet());
        
        message = new Message("get_view_data");
        message.add("view", view);
        message.add("init", pagectx.isInitializableView());
        message.setSessionid(complexid);
        
        initparams = pagectx.getInitParameters();
        if (initparams == null || initparams.length == 0) {
            parameters = pagectx.getParameters();
        } else {
            parameters = new HashMap<>();
            iparams = pagectx.getParameters();
            for (String name : initparams)
                parameters.put(name, iparams.get(name));
            pagectx.setInitParameters(null);
        }
        
        for (String name : parameters.keySet())
            view.export(name, parameters.get(name));
        try {
            service = new Service(complexid, composeUrl(appname));
            view = (View)service.call(message);
            
            input = new Input();
            input.view = view;
            input.view.clearMultipartElements();
            input.container = null;
            input.function = this;
            
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
        
        return view;
    }
    
    /**
     * 
     * @param contextdata
     * @return
     */
    private final PageContext createPageContext(ContextData contextdata) {
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
     * @param req
     * @param sessionid
     * @param logid
     * @param function
     * @return
     */
    private final PageContext createTicketContext(HttpServletRequest req,
            String sessionid, int logid, Function function) {
        PageContext pagectx;
        ContextData contextdata = new ContextData();
        String ticketcode = req.getParameter("ticket");
        AccessTicket ticket = tickets.get(ticketcode);
        
        contextdata.appname = ticket.getAppname();
        contextdata.pagename = ticket.getPagename();
        contextdata.logid = logid;
        contextdata.sessionid = sessionid;
        contextdata.initialize = true;
        
        pagectx = createPageContext(contextdata);
        pagectx.addParameter("username", ticket.getUsername());
        pagectx.addParameter("secret", ticket.getSecret());
        pagectx.addParameter("locale", ticket.getLocale());
        pagectx.addParameter("ticket", ticketcode);
        return pagectx;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.internal.AbstractRenderer#entry(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse,
     *     boolean)
     */
    @Override
    protected final void entry(HttpServletRequest req, HttpServletResponse resp,
            boolean keepsession) throws Exception {
        String sessionid;
        int logid = 0;
        PageContext pagectx = null;
        
        req.setCharacterEncoding("UTF-8");
        sessionid = req.getSession().getId();
        
        try {
            if (apps.containsKey(sessionid)) {
                if (keepsession)
                    pagectx = getPageContext(req);
                logid = apps.get(sessionid).size();
            }
            
            if (pagectx == null) {
                tickets.load(this);
                if (!hasTicket(req))
                    pagectx = createLoginContext(req, sessionid, logid);
                else
                    pagectx = createTicketContext(req, sessionid, logid, this);
            }
            
            if (pagectx.getViewData() != null)
                pagectx = processController(req, pagectx, sessionid);
            
            startRender(sessionid, resp, pagectx);
        } catch (Exception e) {
            pagectx = createExceptionContext(sessionid, pagectx, e);
            resp.reset();
            startRender(sessionid, resp, pagectx);
        }
    }

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
        service = new Service(complexid, url);
        service.call(message);
    }

    @Override
    public final <T extends Handler> T get(String handler) {
        return null;
    }
    
    /**
     * 
     * @param req
     * @param sessionctx
     * @return
     */
    @SuppressWarnings("unchecked")
    private final PageContext getPageContext(HttpServletRequest req)
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
            pagestackitem = home(getComplexId(contextdata.sessionid, logid));
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
     * @param contextdata
     * @return
     */
    private final PageContext getPageContext(ContextData contextdata) {
        AppContext appctx;
        List<SessionContext> sessions = apps.get(contextdata.sessionid);
        
        if (contextdata.logid >= sessions.size())
            return null;
        
        appctx = sessions.get(contextdata.logid).getAppContext(
                contextdata.appname);
        if (appctx == null)
            return null;
        
        return appctx.getPageContext(contextdata.pagename);
    }
    
    /**
     * 
     * @param sessionid
     * @return
     */
    public static final PageStackItem[] getPagesPositions(String sessionid) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return apps.get(complexid[0]).get(logid).getPagesNames();
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
        service = new Service(complexid, url);
        return (String)service.call(message);
    }
    
    /**
     * 
     * @param getSessionId()
     * @param appname
     * @param pagename
     * @param logid
     * @return
     */
    public static final View getView(String sessionid, String appname,
            String pagename) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        AppContext appcontext = apps.get(complexid[0]).get(logid).
                getAppContext(appname);
        
        return appcontext.getPageContext(pagename).getViewData();
    }
    
    /**
     * 
     * @param req
     * @return
     */
    private final boolean hasTicket(HttpServletRequest req) {
        String ticket = req.getParameter("ticket");
        
        if (ticket == null)
            return false; 
        
        return tickets.has(ticket);
    }
    
    /**
     * 
     * @param getSessionId()
     * @return
     */
    public static final PageStackItem home(String sessionid) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return apps.get(complexid[0]).get(logid).home();
    }
    
    /**
     * 
     * @param ctxdata
     * @return
     */
    private final boolean isConnected(ContextData ctxdata) {
        String url;
        Message message;
        Service service;
        String complexid = getComplexId(ctxdata.sessionid, ctxdata.logid);
        
        message = new Message("is_connected");
        message.setSessionid(complexid);
        
        url = new StringBuilder(getServerName()).append(Iocaste.SERVERNAME).
                toString();
        service = new Service(complexid, url);
        return (boolean)service.call(message);
    }
    
    /**
     * 
     * @param appname
     * @param complexid
     * @return
     */
    private final boolean isExecuteAuthorized(String appname, String complexid)
    {
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
        service = new Service(complexid, url);
        return (Boolean)service.call(message);
    }
    
    /**
     * 
     * @param getSessionId()
     * @param logid
     * @return
     */
    public static final PageStackItem popPage(String sessionid) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return apps.get(complexid[0]).get(logid).popPage();
    }
    
    /**
     * 
     * @param req
     * @param pagectx
     * @param sessionid
     * @return
     * @throws Exception
     */
    private final PageContext processController(HttpServletRequest req,
            PageContext pagectx, String sessionid) throws Exception {
        long sequence;
        ControllerData config;
        ContextData contextdata;
        ControlComponent action;
        Enumeration<?> parameternames;
        PageContext pagectx_;
        Map<String, String[]> parameters;
        View pagectxview;
        String appname, pagename, key, pagetrack = null, actionname = null;
        
        /*
         * obtem parâmetros da requisição
         */
        if (ServletFileUpload.isMultipartContent(req)) {
            parameters = processMultipartContent(req, pagectx);
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
        callController(config);
        
        /*
         * processa atualização na visão após chamada do controlador
         */
        action = config.state.view.getElement(actionname);
        if (config.state.pagecall && (action == null ||
                !action.isCancellable() || action.allowStacking()))
            pushPage(config.sessionid, config.state.view.getAppName(),
                    config.state.view.getPageName());
        
        config.state.view.clearInputs();
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
                config.state.rapp != null) {
            pagectx.setError(AUTHORIZATION_ERROR);
            pagectx.getViewData().message(Const.ERROR, "user.not.authorized");
            
            return pagectx;
        }
        
        pagectx.setShControl(config.shcontrol);
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
        
        if (!config.event)
            sequence++;
        
        pagectx_.setInitialize(config.state.initialize);
        pagectx_.setSequence(sequence);
        if (config.state != null) {
            pagectx_.setKeepView(config.state.keepview);
            pagectx_.setReloadableView(config.state.reloadable);
        } else {
            pagectx_.setKeepView(true);
            pagectx_.setReloadableView(false);
        }
        
        pagectx_.setInitParameters(config.state.view.getInitParameters());
        pagectx_.clearParameters();
        pagectxview = pagectx_.getViewData();
        
        if (pagectxview != null)
            pagectxview.message(config.state.view.getMessageType(),
                    config.state.view.getTranslatedMessage());
        
        for (String name : config.state.view.getExportable())
            pagectx_.addParameter(name, config.state.view.getParameter(name));
        config.state.view.clearInitExports();
        
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
        MultipartElement[] elements;
        FileItem fileitem;
        Map<String, String[]> parameters;
        
        parameters = new HashMap<>();
        elements = pagectx.getViewData().getMultipartElements();
        
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

            for (MultipartElement element : elements) {
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
    public static final void pushPage(String sessionid, String appname,
            String pagename) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        apps.get(complexid[0]).get(logid).pushPage(appname, pagename);
    }
    
    /**
     * 
     * @param ticketcode
     * @param function
     */
    public static final void removeTicket(String ticketcode, Function function)
    {
        tickets.remove(ticketcode, function);
    }
    
    /**
     * 
     * @param sessionid
     * @param position
     */
    public static final void setPagesPosition(
            String sessionid, String position) {
        SessionContext context;
        String[] composition;
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        context = apps.get(complexid[0]).get(logid);
        composition = position.split("\\.");
        context.setPagesPosition(composition[0], composition[1]);
    }
    
    /**
     * 
     * @param resp
     * @param pagectx
     * @throws Exception
     */
    private final void startRender(String sessionid, HttpServletResponse resp,
            PageContext pagectx) throws Exception {
        TrackingData tracking;
        HtmlRenderer renderer;
        StyleSheet userstyle;
        String username, viewmessage;
        Const messagetype;
        AppContext appctx;
        View view;
        Map<String, Object> parameters;

        /*
         * prepara a visão para renderização
         */
        view = pagectx.getViewData();
        if (view != null) {
            viewmessage = view.getTranslatedMessage();
            messagetype = view.getMessageType();
        } else {
            viewmessage = null;
            messagetype = null;
        }
        
        if (pagectx.getError() == 0 &&
                (view == null || pagectx.isReloadableView())) {
            view = createView(sessionid, pagectx);
            pagectx.setViewData(view);
        } else {
            parameters = pagectx.getParameters();
            for (String key : parameters.keySet())
                view.export(key, parameters.get(key));
        }
        /*
         * ajusta e chama o renderizador
         */
        username = pagectx.getUsername();
        userstyle = view.styleSheetInstance();
        appctx = pagectx.getAppContext();
        if (userstyle != null)
            appctx.setStyleSheet(userstyle.getElements());
        
        renderer = new HtmlRenderer();
        renderer.setMessageSource(msgsource);
        renderer.setMessageText(viewmessage);
        renderer.setMessageType(messagetype);
        renderer.setUsername((username == null)? NOT_CONNECTED : username);
        
        if (dbname == null)
            dbname = new Iocaste(this).getSystemParameter("dbname");
        
        renderer.setDBName(dbname);
        renderer.setShControl(pagectx.getShControl());
        renderer.setFunction(this);
        tracking = new TrackingData();
        tracking.logid = pagectx.getLogid();
        tracking.sequence = pagectx.getSequence();
        tracking.sessionid = sessionid;
        tracking.contexturl = pagectx.getContextUrl();
        render(renderer, resp, view, tracking);
        
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
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        AppContext appcontext = apps.get(complexid[0]).get(logid).
                getAppContext(view.getAppName());
        Input input = new Input();
        
        input.view = view;
        input.view.clearMultipartElements();
        input.container = null;
        input.function = function;
        
        for (Container container : view.getContainers()) {
            input.element = container;
            input.register();
        }
        
        appcontext.getPageContext(view.getPageName()).setViewData(view);
    }
}

class ContextData {
    public String sessionid, appname, pagename;
    public int logid;
    public boolean initialize;
}