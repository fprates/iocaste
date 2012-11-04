package org.iocaste.shell;

import java.io.File;
import java.io.FileNotFoundException;
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
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.internal.AbstractRenderer;
import org.iocaste.internal.Common;
import org.iocaste.internal.Controller;
import org.iocaste.internal.HtmlRenderer;
import org.iocaste.internal.InputStatus;
import org.iocaste.internal.ControllerData;
import org.iocaste.internal.TrackingData;
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
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.MultipartElement;
import org.iocaste.shell.common.RangeInputComponent;
import org.iocaste.shell.common.SHLib;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class PageRenderer extends AbstractRenderer {
    private static final long serialVersionUID = -8143025594178489781L;
    private static final String NOT_CONNECTED = "not.connected";
    private static final String EXCEPTION_HANDLER = "iocaste-exhandler";
    private static final byte AUTHORIZATION_ERROR = 1;
    private static Map<String, List<SessionContext>> apps =
            new HashMap<String, List<SessionContext>>();
    private String sessionconnector, dbname;
    private Map<String, Map<String, String>> style;
    private MessageSource msgsource;
    
    public PageRenderer() {
        style = null;
        sessionconnector = "iocaste-login";
        msgsource = Messages.getMessages();
    }
    
    /**
     * 
     * @param config
     * @return
     * @throws Exception
     */
    private final View callController(ControllerData config) throws Exception {
        InputStatus status;
        Message message;
        ControlComponent control;
        
        status = Controller.validate(config);
        if (status.fatal != null)
            throw new IocasteException(status.fatal);
        
        config.event = status.event;
        if (status.error > 0 || status.event)
            return config.view;
        
        message = new Message();
        message.setId("exec_action");
        message.add("view", config.view);
        message.setSessionid(config.sessionid);
        
        for (String name : config.values.keySet())
            message.add(name, config.values.get(name));

        control = config.view.getElement(message.getString("action"));
        if (control != null && control.getType() == Const.SEARCH_HELP) {
            config.view.setParameter("sh", control);
            config.view.redirect("iocaste-search-help", "main");
            config.view.setReloadableView(true);
        } else {
            try {
                config.view = (View)Service.callServer(
                        composeUrl(config.contextname), message);
                
                if (config.view.getMessageType() == Const.ERROR)
                    Common.rollback(getServerName(), config.sessionid);
                else
                    Common.commit(getServerName(), config.sessionid);
            } catch (Exception e) {
                Common.rollback(getServerName(), config.sessionid);
                throw e;
            }
        }
        
        return config.view;
    }
    
    /**
     * 
     * @param app
     * @return
     */
    private final String composeUrl(String app) {
        return new StringBuffer(getServerName()).append("/").
                append(app).append("/view.html").toString();
    }
    
    /**
     * 
     * @param expagectx
     * @param exception
     * @return
     * @throws Exception
     */
    private final PageContext createExceptionContext(PageContext expagectx,
            Exception exception) throws Exception {
        PageContext pagectx;
        ContextData contextdata = new ContextData();
        
        contextdata.sessionid = getSessionId();
        contextdata.appname = EXCEPTION_HANDLER;
        contextdata.pagename = "main";
        contextdata.logid = expagectx.getLogid();
        
        pagectx = createPageContext(contextdata);
        pagectx.addParameter("exception", exception);
        pagectx.addParameter("exview", expagectx.getViewData());
        pagectx.setUsername(expagectx.getUsername());
        
        return pagectx;
    }
    
    /**
     * 
     * @param logid
     * @return
     */
    private final PageContext createLoginContext(int logid) {
        ContextData contextdata = new ContextData();
        
        contextdata.sessionid = getSessionId();
        contextdata.appname = sessionconnector;
        contextdata.pagename = "authentic";
        contextdata.logid = logid;
        return createPageContext(contextdata);
    }
    
    /**
     * 
     * @param pagectx
     * @return
     * @throws Exception
     */
    private final View createView(PageContext pagectx) throws Exception {
        String sessionid, appname, pagename;
        int logid;
        InputData inputdata;
        Message message;
        Map<String, Object> iparams, parameters;
        String[] initparams;
        AppContext appctx;
        View view;
        
        appctx = pagectx.getAppContext();
        appname = appctx.getName();
        pagename = pagectx.getName();
        logid = pagectx.getLogid();
        sessionid = getComplexId(getSessionId(), logid);
        
        if (appname == null || pagename == null)
            throw new IocasteException("page not especified.");
        
        if (appctx.getStyleSheet() == null) {
            if (style == null)
                style = Style.get("DEFAULT", this);
                
            appctx.setStyleSheet(style);
        }
        
        view = new View(appname, pagename);
        message = new Message();
        message.setId("get_view_data");
        message.add("view", view);
        message.setSessionid(sessionid);
        
        initparams = pagectx.getInitParameters();
        if (initparams == null || initparams.length == 0) {
            parameters = pagectx.getParameters();
        } else {
            parameters = new HashMap<String, Object>();
            iparams = pagectx.getParameters();
            for (String name : initparams)
                parameters.put(name, iparams.get(name));
            pagectx.setInitParameters(null);
        }
        
        for (String name : parameters.keySet())
            view.export(name, parameters.get(name));
        try {
            view = (View)Service.callServer(composeUrl(appname), message);
            Common.commit(getServerName(), sessionid);
        } catch (Exception e) {
            Common.rollback(getServerName(), sessionid);
            throw e;
        }
        
        inputdata = new InputData();
        inputdata.view = view;
        inputdata.container = null;
        inputdata.function = this;
        
        for (Container container : view.getContainers()) {
            inputdata.element = container;
            registerInputs(inputdata);
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
            sessions = new ArrayList<SessionContext>();
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
        appctx.put(contextdata.pagename, pagectx);
        sessionctx.put(contextdata.appname, appctx);
        
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
        
        try {
            sessionid = getSessionId();
            if (apps.containsKey(sessionid)) {
                if (keepsession)
                    pagectx = getPageContext(req, sessionid);
                logid = apps.get(sessionid).size();
            }
            
            if (pagectx == null)
                pagectx = createLoginContext(logid);
            
            if (pagectx.getViewData() != null)
                pagectx = processController(req, pagectx, sessionid, this);
            
            startRender(resp, pagectx);
        } catch (Exception e) {
            pagectx = createExceptionContext(pagectx, e);
            resp.reset();
            startRender(resp, pagectx);
        }
    }

    /**
     * 
     * @param appname
     * @param complexid
     */
    private final void execute(String appname, String complexid) {
        String url;
        Message message = new Message();
        
        message.setId("set_current_app");
        message.add("current_app", appname);
        message.setSessionid(complexid);
        
        url = new StringBuilder(getServerName()).append(Iocaste.SERVERNAME).
                toString();
        Service.callServer(url, message);
    }
    
    /**
     * 
     * @param input
     * @param inputdata
     */
    private static final void generateSearchHelp(InputComponent input,
            InputData inputdata) {
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
     * @param getSessionId()
     * @param logid
     * @return
     */
    private final String getComplexId(String sessionid, int logid) {
        return new StringBuilder(getSessionId()).append(":").append(logid).
                toString();
    }
    
    /**
     * 
     * @param pagetrack
     * @return
     */
    private static final int getLogid(String pagetrack) {
        String[] parsed = pagetrack.split(":");
        
        return Integer.parseInt(parsed[2]);
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
        long sequence;
        PageContext pagectx;
        List<FileItem> files = null;
        String pagetrack = null;
        
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
            pageparse = home(getComplexId(contextdata.sessionid, logid));
            if (pageparse != null) {
                contextdata.appname = pageparse[0];
                contextdata.pagename = pageparse[1];
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
        List<SessionContext> sessions = apps.get(getSessionId());
        
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
     * @param appname
     * @return
     */
    public static final Map<String, Map<String, String>> getStyleSheet(
            String sessionid, String appname) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return apps.get(complexid[0]).get(logid).getAppContext(appname).
                getStyleSheet();
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
     * @param getSessionId()
     * @return
     */
    public static final String[] home(String sessionid) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return apps.get(complexid[0]).get(logid).home();
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
        Authorization authorization = new Authorization("APPLICATION.EXECUTE");
        
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", appname);
        
        message = new Message();
        message.setId("is_authorized");
        message.add("authorization", authorization);
        message.setSessionid(complexid);
        
        url = new StringBuilder(getServerName()).append(Iocaste.SERVERNAME).
                toString();
        return (Boolean)Service.callServer(url, message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    private final boolean isSessionConnector(String name) {
        return sessionconnector.equals(name);
    }
    
    /**
     * 
     * @param getSessionId()
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
     * @param req
     * @param pagectx
     * @param sessionid
     * @param function
     * @return
     * @throws Exception
     */
    private final PageContext processController(HttpServletRequest req,
            PageContext pagectx, String sessionid, Function function)
                    throws Exception {
        long sequence;
        Iocaste iocaste;
        ControllerData config;
        ContextData contextdata;
        InputData inputdata;
        ControlComponent action;
        Enumeration<?> parameternames;
        PageContext pagectx_;
        Map<String, String[]> parameters;
        View view;
        String appname, pagename, key, pagetrack = null, actionname = null;
        
        /*
         * obtem parâmetros da requisição
         */
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

        /*
         * prepara contexto e chama controlador
         */
        if (parameters.containsKey("pagetrack")) {
            pagetrack = parameters.get("pagetrack")[0];
            parameters.remove("pagetrack");
        }
        
        config = new ControllerData();
        config.view = pagectx.getViewData();
        config.values = parameters;
        config.function = this;
        config.contextname = pagectx.getAppContext().getName();
        config.logid = getLogid(pagetrack);
        config.sessionid = getComplexId(sessionid, config.logid);
        config.servername = getServerName();
        
        view = callController(config);
        
        /*
         * processa atualização na visão após chamada do controlador
         */
        action = view.getElement(actionname);
        if (view.hasPageCall() && (action == null ||
                !action.isCancellable() || action.allowStacking()))
            pushPage(config.sessionid, view.getAppName(), view.getPageName());
        
        view.clearInputs();
        inputdata = new InputData();
        inputdata.view = view;
        inputdata.container = null;
        inputdata.function = this;
        for (Container container : view.getContainers()) {
            inputdata.element = container;
            registerInputs(inputdata);
        }
        
        updateView(config.sessionid, view, this);
        
        /*
         * prepara retorno para resposta, seja na visão atual ou se for
         * redirecionado
         */
        appname = view.getRedirectedApp();
        if (appname == null)
            appname = pagectx.getAppContext().getName();
        
        pagename = view.getRedirectedPage();
        if (pagename == null)
            pagename = pagectx.getName();
        
        /*
         * testa autorização para execução e sequencia de telas
         */
        if (!isExecuteAuthorized(appname, config.sessionid) &&
                view.getRedirectedApp() != null) {
            pagectx.setError(AUTHORIZATION_ERROR);
            pagectx.getViewData().message(Const.ERROR, "user.not.authorized");
            
            return pagectx;
        }
        
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
            
        pagectx_.setSequence(sequence);
        pagectx_.setReloadableView(view.isReloadableView());
        pagectx_.setInitParameters(view.getInitParameters());
        pagectx_.clearParameters();
        for (String name : view.getExportable())
            pagectx_.addParameter(name, view.getParameter(name));
        view.clearInitExports();
        
        iocaste = new Iocaste(function);
        if (iocaste.isConnected()) {
            appname = view.getAppName();
            execute(appname, config.sessionid);
            if (isSessionConnector(appname))
               pagectx_.setUsername((String)view.getParameter("username"));
            return pagectx_;
        }
        
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
     * @param inputdata
     */
    private static final void registerInputs(InputData inputdata) {
        RangeInputComponent rinput;
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
            input = (InputComponent)inputdata.element;
            inputdata.view.addInput(input.getHtmlName());
            
            modelitem = input.getModelItem();
            if (input.getSearchHelp() == null && modelitem != null &&
                    modelitem.getSearchHelp() != null)
                generateSearchHelp(input, inputdata);
            
            if (input.isValueRangeComponent()) {
                rinput = (RangeInputComponent)input;
                inputdata.view.addInput(rinput.getHighHtmlName());
                inputdata.view.addInput(rinput.getLowHtmlName());
            }
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
    private final void startRender(HttpServletResponse resp,
            PageContext pagectx) throws Exception {
        TrackingData tracking;
        HtmlRenderer renderer;
        Map<String, Map<String, String>> userstyle;
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
            view = createView(pagectx);
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
        userstyle = view.getStyleSheet();
        appctx = pagectx.getAppContext();
        if (userstyle != null)
            appctx.setStyleSheet(userstyle);
        
        renderer = getRenderer();
        renderer.setMessageSource(msgsource);
        renderer.setMessageText(viewmessage);
        renderer.setMessageType(messagetype);
        renderer.setUsername((username == null)? NOT_CONNECTED : username);
        renderer.setCssElements(appctx.getStyleSheet());
        
        if (dbname == null)
            dbname = new Iocaste(this).getSystemParameter("dbname");
        
        renderer.setDBName(dbname);
        tracking = new TrackingData();
        tracking.logid = pagectx.getLogid();
        tracking.sequence = pagectx.getSequence();
        tracking.sessionid = getSessionId();
        render(view, tracking);
        
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
    public View view;
    public Element element;
    public Container container;
    public Function function;
}

class ContextData {
    public String sessionid, appname, pagename;
    public int logid;
}