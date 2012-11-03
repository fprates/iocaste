package org.iocaste.install;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.internal.AbstractRenderer;
import org.iocaste.internal.Controller;
import org.iocaste.internal.ControllerData;
import org.iocaste.internal.HtmlRenderer;
import org.iocaste.internal.InputStatus;
import org.iocaste.internal.TrackingData;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.MultipartElement;
import org.iocaste.shell.common.SHLib;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Main extends AbstractRenderer {
    private static final long serialVersionUID = -8143025594178489781L;
    private static final String NOT_CONNECTED = "not.connected";
    private static final String INSTALLER = "iocaste-install";
    private static Map<String, List<SessionContext>> apps =
            new HashMap<String, List<SessionContext>>();
    private Stages stage;
    private MessageSource msgsource;
    private Function installapp;
    
    public Main() {
        stage = Stages.WELCOME;
        installapp = new Install();
//        msgsource = Messages.getMessages();
    }
    
    /**
     * 
     * @param req
     * @param url
     * @return
     * @throws Exception
     */
    private final View callController(ControllerData config) throws Exception {
        InputStatus status;
        Message message;
        
        status = Controller.validate(config);
        if (status.fatal != null)
            throw new IocasteException(status.fatal);
        
        if (status.error > 0)
            return config.view;
        
        message = new Message();
        message.setId("exec_action");
        message.add("view", config.view);
        message.setSessionid(config.sessionid);
        
        for (String name : config.values.keySet())
            message.add(name, config.values.get(name));
        
        installapp.setServletContext(getServletContext());
        
        return (View)installapp.run(message);
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
        
        if (!apps.containsKey(getSessionId())) {
            sessions = new ArrayList<SessionContext>();
            apps.put(getSessionId(), sessions);
            
            sessionctx = new SessionContext();
            sessions.add(sessionctx);
        } else {
            sessions = apps.get(getSessionId());
            
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
        Iocaste iocaste;
        ContextData contextdata;
        int logid = 0;
        PageContext pagectx = null;
        
        req.setCharacterEncoding("UTF-8");
        
        if (apps.containsKey(getSessionId())) {
            if (keepsession)
                pagectx = getPageContext(req, getSessionId());
            logid = apps.get(getSessionId()).size();
        }
        
        if (pagectx == null) {
            contextdata = new ContextData();
            contextdata.sessionid = getSessionId();
            contextdata.appname = INSTALLER;
            contextdata.pagename = stage.toString();
            contextdata.logid = logid;
            
            pagectx = createPageContext(contextdata);
        }
        
        if (pagectx.getViewData() != null) {
            iocaste = new Iocaste(this);
            pagectx = processController(iocaste, req, pagectx);
        }
        
        startRender(resp, pagectx);
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
        
        return Integer.parseInt(parsed[1]);
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
    private final PageContext getPageContext(HttpServletRequest req,
            String sessionid) throws Exception {
        ContextData contextdata;
        String[] pageparse;
        int t, logid;
        PageContext pagectx;
        String pagetrack = null;
        
        pagetrack = req.getParameter("pagetrack");
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
        contextdata.sessionid = getSessionId();
        contextdata.appname = pageparse[0];
        contextdata.pagename = pageparse[1];
        contextdata.logid = logid;
        
        pagectx = getPageContext(contextdata);
        
        if (pagectx == null)
            return null;
        
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
        
        return (appctx == null)? null : appctx.getPageContext(
                contextdata.pagename);
    }
    
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
     * @param iocaste
     * @param req
     * @param pagepos
     * @return
     * @throws Exception
     */
    private final PageContext processController(Iocaste iocaste,
            HttpServletRequest req, PageContext pagectx) throws Exception {
        ControllerData config;
        ContextData contextdata;
        InputData inputdata;
        ControlComponent action;
        Enumeration<?> parameternames;
        PageContext pagectx_;
        Map<String, String[]> parameters;
        View view;
        String appname, pagename, key, pagetrack = null, actionname = null;
        
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

        if (parameters.containsKey("pagetrack")) {
            pagetrack = parameters.get("pagetrack")[0];
            parameters.remove("pagetrack");
        }
        
        config = new ControllerData();
        config.view = pagectx.getViewData();
        config.values = parameters;
        config.contextname = pagectx.getAppContext().getName();
        config.logid = getLogid(pagetrack);
        config.sessionid = getComplexId(getSessionId(), config.logid);
        view = callController(config);
        
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
        appname = view.getRedirectedApp();
        if (appname == null)
            appname = pagectx.getAppContext().getName();
        
        pagename = view.getRedirectedPage();
        if (pagename == null)
            pagename = pagectx.getName();
        
        pagectx.setError((byte)0);
        
        contextdata = new ContextData();
        contextdata.sessionid = getSessionId();
        contextdata.appname = appname;
        contextdata.pagename = pagename;
        contextdata.logid = config.logid;
        
        pagectx_ = getPageContext(contextdata);
        if (pagectx_ == null)
            pagectx_ = createPageContext(contextdata);
        
        pagectx_.setReloadableView(view.isReloadableView());
        pagectx_.clearParameters();
        for (String name : view.getExportable())
            pagectx_.addParameter(name, view.getParameter(name));
        
        return pagectx_;
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
    private final void startRender(HttpServletResponse resp, PageContext pagectx)
            throws Exception {
        TrackingData tracking;
        HtmlRenderer renderer;
        Map<String, Map<String, String>> userstyle;
        String username, viewmessage;
        Const messagetype;
        int logid;
        InputData inputdata;
        AppContext appctx;
        View viewdata;
        Map<String, Object> parameters;
        Message message = new Message();

        appctx = pagectx.getAppContext();
        viewdata = pagectx.getViewData();
        if (viewdata != null) {
            viewmessage = viewdata.getTranslatedMessage();
            messagetype = viewdata.getMessageType();
        } else {
            viewmessage = null;
            messagetype = null;
        }
        
        if (pagectx.getError() == 0 &&
                (viewdata == null || pagectx.isReloadableView())) {
            logid = pagectx.getLogid();
            
            message.setId("get_view_data");
            message.add("app", appctx.getName());
            message.add("page", pagectx.getName());
            message.add("parameters", pagectx.getParameters());
            message.setSessionid(getComplexId(getSessionId(), logid));
            
            viewdata = (View)installapp.run(message);
            
            inputdata = new InputData();
            inputdata.view = viewdata;
            inputdata.container = null;
            inputdata.function = this;
            
            for (Container container : viewdata.getContainers()) {
                inputdata.element = container;
                registerInputs(inputdata);
            }
            
            pagectx.setViewData(viewdata);
        } else {
            parameters = pagectx.getParameters();
            for (String key : parameters.keySet())
                viewdata.export(key, parameters.get(key));
        }

        /*
         * ajusta o renderizador
         */
        username = pagectx.getUsername();
        userstyle = viewdata.getStyleSheet();
        if (userstyle != null)
            appctx.setStyleSheet(userstyle);
        
        renderer = getRenderer();
        renderer.setMessageSource(msgsource);
        renderer.setMessageText(viewmessage);
        renderer.setMessageType(messagetype);
        renderer.setUsername((username == null)? NOT_CONNECTED : username);
        renderer.setCssElements(appctx.getStyleSheet());
        
        tracking = new TrackingData();
        tracking.sessionid = getSessionId();
        tracking.logid = pagectx.getLogid();
        render(viewdata, tracking);
        
        pagectx.setActions(renderer.getActions());
    }
    
    /**
     * 
     * @param getSessionId()
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void updateView(String sessionid, View view,
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
    public View view;
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