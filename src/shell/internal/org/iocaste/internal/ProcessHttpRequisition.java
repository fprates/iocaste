package org.iocaste.internal;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.StandardService;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.shell.common.AccessTicket;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.MultipartElement;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.PopupControl;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class ProcessHttpRequisition extends AbstractHandler {
    private static final String NOT_CONNECTED = "not.connected";
    private static final byte AUTHORIZATION_ERROR = 1;
    private static final String STD_CONTENT = "text/html";
    public Map<String, List<SessionContext>> apps;
    public TicketControl tickets;
    protected boolean disconnecteddb;
    
    public ProcessHttpRequisition() {
        tickets = new TicketControl();
        apps = new HashMap<>();
    }
    
    private final void callController(ControllerData config) throws Exception {
        InputStatus status;
        Message message;
        ControlComponent control;
        Service service;
        AbstractRenderer function;
        
        status = Controller.validate(config);
        if (status.fatal != null)
            throw new IocasteException(status.fatal);
        
        config.event = status.event;
        if ((status.msgtype == Const.ERROR) || status.event)
            return;
        
        message = new Message("exec_action");
        message.add("view", config.state.view);
        message.setSessionid(config.sessionid);
        
        for (String name : config.values.keySet())
            message.add(name, config.values.get(name));

        control = config.state.view.getElement(message.getst("action"));
        if ((control != null) && control.isPopup()) {
            config.popupcontrol = (PopupControl)control;
            config.contexturl = composeUrl(
                    config.popupcontrol.getApplication());
            return;
        }
        
        function = getFunction();
        try {
            service = new StandardService(config.sessionid,
                    composeUrl(config.contextname));
            
            config.state = (ViewState)service.call(message);
            if (config.state.messagetype == Const.ERROR)
                Common.rollback(function.getServerName(),
                        config.sessionid, disconnecteddb);
            else
                Common.commit(function.getServerName(),
                        config.sessionid, disconnecteddb);
        } catch (Exception e) {
            if (getPagesPositions(config.sessionid).length == 1)
                AbstractRenderer.pushPage(config.sessionid, config.state.view);
            
            Common.rollback(function.getServerName(),
                    config.sessionid, disconnecteddb);
            throw e;
        }
    }
    
    /**
     * 
     * @param app
     * @return
     */
    protected final String composeUrl(String app) {
        AbstractRenderer function = getFunction();
        return new StringBuffer(function.getServerName()).append("/").
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
    
    private PageContext createContext(RendererContext context, int logid) {
        tickets.load(getFunction());
        return (!hasTicket(context.req))? createStartContext(context, logid) :
            createTicketContext(context, logid);
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
        Function function = getFunction();
        
        contextdata.sessionid = sessionid;
        contextdata.appname = getConfiguration(function, "EXCEPTION_HANDLER");
        if (contextdata.appname == null)
            contextdata.appname = "iocaste-exhandler";
        if (contextdata.locale == null)
            contextdata.locale = getLocale(contextdata, function);
        
        contextdata.pagename = "main";
        contextdata.logid = expagectx.getLogid();
        contextdata.initialize = true;
        
        pagectx = createPageContext(contextdata);
        pagectx.parameters.put("exception", exception);
        pagectx.parameters.put("exview", expagectx.getViewData());
        pagectx.setUsername(expagectx.getUsername());
        
        return pagectx;
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
        String[] locale;
        
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
        
        locale = contextdata.locale.split("_");
        pagectx = new PageContext(contextdata.pagename);
        pagectx.setAppContext(appctx);
        pagectx.setLogid(contextdata.logid);
        pagectx.setInitialize(contextdata.initialize);
        if (locale.length == 2)
            pagectx.locale = new Locale(locale[0], locale[1]);
        else
            pagectx.locale = new Locale(locale[0]);
        appctx.put(contextdata.pagename, pagectx);
        sessionctx.put(contextdata.appname, appctx);
        
        return pagectx;
    }
    
    /**
     * Cria uma página de context para conexão.
     * @param sessionid
     * @param logid
     * @return
     */
    private final PageContext createStartContext(
            RendererContext context, int logid) {
        SessionContext sessionctx;
        PageContext pagectx;
        Enumeration<String> parameternames;
        String key, startpage;
        ContextData contextdata;
        Function function = getFunction();
        
        contextdata = new ContextData();
        contextdata.appname = context.req.getParameter("login-manager");
        if (contextdata.appname == null)
            contextdata.appname = getConfiguration(function, "LOGIN_MANAGER");
        if (contextdata.appname == null)
            contextdata.appname = "iocaste-login";
        contextdata.sessionid = context.sessionid;
        contextdata.logid = logid;
        contextdata.initialize = true;
        contextdata.locale = getLocale(contextdata, function);
        
        startpage = context.req.getParameter("start-page");
        if (startpage == null)
            startpage = "authentic";
        contextdata.pagename = startpage;
        
        pagectx = createPageContext(contextdata);
        sessionctx = apps.get(context.sessionid).get(logid);
        sessionctx.loginapp = new StringBuilder(contextdata.appname).
            append(".").append(contextdata.pagename).toString();

        parameternames = context.req.getParameterNames();
        while (parameternames.hasMoreElements()) {
            key = (String)parameternames.nextElement();
            pagectx.parameters.put(key, context.req.getParameterValues(key)[0]);
        }
        
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
    private final PageContext createTicketContext(RendererContext context,
            int logid) {
        PageContext pagectx;
        ContextData contextdata = new ContextData();
        String ticketcode = context.req.getParameter("ticket");
        AccessTicket ticket = tickets.get(ticketcode);
        
        contextdata.appname = ticket.getAppname();
        contextdata.pagename = ticket.getPagename();
        contextdata.logid = logid;
        contextdata.sessionid = context.sessionid;
        contextdata.initialize = true;
        
        pagectx = createPageContext(contextdata);
        pagectx.parameters.put("username", ticket.getUsername());
        pagectx.parameters.put("secret", ticket.getSecret());
        pagectx.parameters.put("locale", ticket.getLocale());
        pagectx.parameters.put("ticket", ticketcode);
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
    private View createView(RendererContext context, PageContext pagectx)
            throws Exception {
        Object[] viewreturn;
        String complexid, appname, pagename;
        int logid;
        Input input;
        Message message;
        Map<String, Object> parameters;
        AppContext appctx;
        View view;
        Service service;
        StyleSheet stylesheet;
        AbstractRenderer function;
        
        appctx = pagectx.getAppContext();
        appname = appctx.getName();
        pagename = pagectx.getName();
        logid = pagectx.getLogid();
        complexid = getComplexId(context.sessionid, logid);
        
        if (appname == null || pagename == null)
            throw new IocasteException("page not especified.");

        view = pagectx.getViewData();
        if (!pagectx.keepView() || (view == null))
            view = new View(appname, pagename);
        stylesheet = DefaultStyle.instance(view);
        stylesheet.export(view);
        
        message = new Message("get_view_data");
        message.add("view", view);
        message.add("init", pagectx.isInitializableView());
        message.add("parameters", pagectx.parameters);
        message.add("servername", context.hostname);
        message.add("protocol", context.protocol);
        message.add("port", context.port);
        message.add("locale", pagectx.locale);
        message.setSessionid(complexid);
        
        if (pagectx.initparams != null) {
            parameters = new HashMap<>();
            for (String name : pagectx.initparams)
                parameters.put(name, pagectx.parameters.get(name));
            pagectx.initparams = null;
            pagectx.parameters = parameters;
        }
        
        function = getFunction();
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
            input.function = function;
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
            
            Common.commit(function.getServerName(), complexid, disconnecteddb);
        } catch (Exception e) {
            Common.rollback(function.getServerName(), complexid,disconnecteddb);
            throw e;
        }
        
        return view;
    }

    /**
     * 
     * @param appname
     * @param complexid
     */
    private final void execute(String appname, String complexid) {
        String url;
        Service service;
        Message message;
        AbstractRenderer function;
        
        message = new Message("set_current_app");
        message.add("current_app", appname);
        message.setSessionid(complexid);
        
        function = getFunction();
        url = new StringBuilder(function.getServerName()).
                append(Iocaste.SERVERNAME).toString();
        service = new StandardService(complexid, url);
        service.call(message);
    }
    
    /**
     * 
     * @param sessionid
     * @param logid
     * @return
     */
    public static final String getComplexId(String sessionid, int logid) {
        return new StringBuilder(sessionid).append(":").append(logid).
                toString();
    }

    @SuppressWarnings("unchecked")
    private final String getConfiguration(Function function, String name) {
        Object[] objects;
        CheckedSelect select;
        
        select = new CheckedSelect(getFunction());
        select.setFrom("SHELL006");
        select.setWhere("CFGNM = ?", name);
        try {
            objects = select.execute();
            if (objects == null)
                return null;
            return (String)((Map<String, Object>)objects[0]).get("CFGVL");
        } catch (Exception e) {
            return null;
        }
    }
    
    private final String getLocale(ContextData ctxdata, Function function) {
        return (isConnected(ctxdata))?
                getConfiguration(function, "DEFAULT_LANGUAGE") : "pt_BR";
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
    
    /**
     * 
     * @param contextdata
     * @return
     */
    private final PageContext getPageContext(ContextData contextdata) {
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
    private final PageContext getPageContext(RendererContext context)
            throws Exception {
        PageStackItem pagestackitem;
        ContextData contextdata;
        String[] pageparse;
        ServletFileUpload fileupload;
        int t, logid;
        long sequence;
        PageContext pagectx;
        List<FileItem> files = null;
        String pagetrack = null;
        
        /*
         * Obtem rastreamento da sessão
         */
        if (ServletFileUpload.isMultipartContent(context.req)) {
            fileupload = new ServletFileUpload(new DiskFileItemFactory());
            files = fileupload.parseRequest(context.req);
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
            pagetrack = context.req.getParameter("pagetrack");
        }
        
        /*
         * processa rastreamento da sessão e obtem contexto da página
         */
        if (pagetrack == null)
            return null;
        
        pageparse = pagetrack.split(":");
        if (!context.sessionid.equals(pageparse[1]))
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
        contextdata.sessionid = context.sessionid;
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
            pagestackitem = AbstractRenderer.home(
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
     * @param complexid
     * @return
     */
    private final String getUsername(ContextData ctxdata) {
        String url;
        Message message;
        Service service;
        String complexid = ProcessHttpRequisition.
                getComplexId(ctxdata.sessionid, ctxdata.logid);
        AbstractRenderer function = getFunction();
        
        message = new Message("get_username");
        message.setSessionid(complexid);
        
        url = new StringBuilder(function.getServerName()).
                append(Iocaste.SERVERNAME).toString();
        service = new StandardService(complexid, url);
        return (String)service.call(message);
    }
    
    /**
     * 
     * @param sessionid
     * @return
     */
    public final PageStackItem[] getPagesPositions(String sessionid) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return apps.get(complexid[0]).get(logid).getPagesNames();
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
     * @param ctxdata
     * @return
     */
    private boolean isConnected(ContextData ctxdata) {
        String complexid = ProcessHttpRequisition.
                getComplexId(ctxdata.sessionid, ctxdata.logid);
        return isConnected(complexid);
    }
    
    /**
     * 
     * @param complexid
     * @return
     */
    private boolean isConnected(String complexid) {
        String url;
        Message message;
        Service service;
        AbstractRenderer function;
        
        message = new Message("is_connected");
        message.setSessionid(complexid);
        
        function = getFunction();
        url = new StringBuilder(function.getServerName()).
                append(Iocaste.SERVERNAME).toString();
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
        Authorization authorization;
        AbstractRenderer function;
        
        authorization = new Authorization("APPLICATION.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", appname);
        
        message = new Message("is_authorized");
        message.add("authorization", authorization);
        message.setSessionid(complexid);
        
        function = getFunction();
        url = new StringBuilder(function.getServerName()).
                append(Iocaste.SERVERNAME).toString();
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
    private final PageContext processController(RendererContext context,
            PageContext pagectx) throws Exception {
        long sequence;
        ControllerData config;
        ContextData contextdata;
        ControlComponent action;
        Enumeration<?> parameternames;
        PageContext pagectx_;
        Map<String, String[]> parameters;
        String appname, pagename, key;
        String pagetrack = null, actionname = null;
        AbstractRenderer function = getFunction();
        
        /*
         * obtem parâmetros da requisição
         */
        if (ServletFileUpload.isMultipartContent(context.req)) {
            parameters = processMultipartContent(context.req, pagectx);
            actionname = parameters.get("action")[0];
        } else {
            parameters = new HashMap<>();
            parameternames = context.req.getParameterNames();
            while (parameternames.hasMoreElements()) {
                key = (String)parameternames.nextElement();
                
                if (pagectx.isAction(key)) {
                    if (actionname != null)
                        continue;
                    actionname = context.req.getParameterValues(key)[0];
                    parameters.put(
                            "action", context.req.getParameterValues(key));
                } else {
                    parameters.put(key, context.req.getParameterValues(key));
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
        config.function = function;
        config.contextname = pagectx.getAppContext().getName();
        config.logid = getLogid(pagetrack);
        config.sessionid = getComplexId(context.sessionid, config.logid);
        config.servername = function.getServerName();
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
            AbstractRenderer.pushPage(config.sessionid, config.state.view);
        
        config.pagectx.inputs.clear();
        AbstractRenderer.updateView(
                config.sessionid, config.state.view, function);
        
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
                    isConnected(config.sessionid)) {
            pagectx.setError(AUTHORIZATION_ERROR);
            pagectx.message(Const.ERROR,
                    Controller.messages.get("user.not.authorized"), null);
            
            return pagectx;
        }
        
        pagectx.setContextUrl(config.contexturl);
        pagectx.setError((byte)0);
        sequence = pagectx.getSequence();
        contextdata = new ContextData();
        contextdata.sessionid = context.sessionid;
        contextdata.appname = appname;
        contextdata.pagename = pagename;
        contextdata.logid = config.logid;
        contextdata.locale = pagectx.getViewData().getLocale().toString();
        
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
     * @param renderer
     * @param view
     * @param tracking
     * @throws Exception
     */
    private final boolean render(RendererContext context, HtmlRenderer renderer,
            PageContext pagectx, TrackingData tracking) throws Exception {
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
            context.resp.reset();
            configResponse(context.resp, pagectx);
            os = context.resp.getOutputStream();
            bos = new BufferedOutputStream(os);
            
            content = view.getContent();
            if (content != null) {
                bos.write(content);
                context.resp.setContentLength(content.length);
                context.resp.setCharacterEncoding(pagectx.getContentEncoding());
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
        
        configResponse(context.resp, pagectx);
        text = renderer.run(view, tracking);
        writer = context.resp.getWriter();
        if (text != null)
            for (String line : text)
                writer.println(line);
        
        writer.flush();
        writer.close();
        return true;
    }

    @Override
    public Object run(Message message) throws Exception {
        RendererContext context;
        
        context = new RendererContext();
        context.parameters = message.get("parameters");
        run(context);
        return null;
    }
    
    public void run(RendererContext context) throws Exception {
        int logid = 0;
        PageContext pagectx = null;
        
        try {
            if (apps.containsKey(context.sessionid)) {
                if (context.keepsession)
                    pagectx = getPageContext(context);
                logid = apps.get(context.sessionid).size();
            }
            
            if (pagectx == null)
                pagectx = (!disconnecteddb)? createContext(context, logid) :
                    createStartContext(context, logid);
                
            if (pagectx.getViewData() != null)
                pagectx = processController(context, pagectx);
            
            startRender(context, pagectx);
        } catch (Exception e) {
            if (pagectx == null)
                throw e;
            pagectx = createExceptionContext(context.sessionid, pagectx, e);
            context.resp.reset();
            startRender(context, pagectx);
        }
    }
    
    @Override
    public final void setFunction(Function function) {
        if (getFunction() == null)
            super.setFunction(function);
    }
    
    /**
     * 
     * @param resp
     * @param pagectx
     * @throws Exception
     */
    private final void startRender(RendererContext context, PageContext pagectx)
            throws Exception {
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
            view = createView(context, pagectx);
            pagectx.setViewData(view);
        }
        
        /*
         * ajusta e chama o renderizador
         */
        username = pagectx.getUsername();
        renderer = new HtmlRenderer();
        renderer.setPageContext(pagectx);
        renderer.setUsername((username == null)? NOT_CONNECTED : username);
        renderer.setFunction(getFunction());
        tracking = new TrackingData();
        tracking.logid = pagectx.getLogid();
        tracking.sequence = pagectx.getSequence();
        tracking.sessionid = context.sessionid;
        tracking.contexturl = pagectx.getContextUrl();
        hasrendered = render(context, renderer, pagectx, tracking);
        if (hasrendered)
            pagectx.setActions(renderer.getActions());
    }

}
