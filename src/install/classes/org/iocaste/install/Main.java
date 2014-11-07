package org.iocaste.install;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.internal.AbstractRenderer;
import org.iocaste.internal.AppContext;
import org.iocaste.internal.Controller;
import org.iocaste.internal.ControllerData;
import org.iocaste.internal.Input;
import org.iocaste.internal.InputStatus;
import org.iocaste.internal.PageContext;
import org.iocaste.internal.SessionContext;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Handler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class Main extends AbstractRenderer {
    private static final long serialVersionUID = -8143025594178489781L;
    private static final String INSTALLER = "iocaste-install";
    private static Map<String, List<SessionContext>> apps;
    private Stages stage;
    private Function installapp;
    
    static {
        apps = new HashMap<>();
    }
    
    public Main() {
        stage = Stages.WELCOME;
        installapp = new Install();
    }
    
    /**
     * 
     * @param req
     * @param url
     * @return
     * @throws Exception
     */
    private final void callController(ControllerData config) throws Exception {
        InputStatus status;
        Message message;
        
        status = Controller.validate(config);
        if (status.fatal != null)
            throw new IocasteException(status.fatal);
        
        if (status.error > 0)
            return;
        
        message = new Message("exec_action");
        message.add("view", config.state.view);
        message.setSessionid(config.sessionid);
        
        for (String name : config.values.keySet())
            message.add(name, config.values.get(name));
        
        installapp.setServletContext(getServletContext());
        installapp.setServerName(getServerName());
        
        config.state = (ViewState)installapp.run(message);
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
    
    @Override
    protected final View createView(String sessionid, PageContext pagectx)
            throws Exception {
        Message message;
        int logid;
        AppContext appctx;
        
        logid = pagectx.getLogid();
        appctx = pagectx.getAppContext();
        
        message = new Message("get_view_data");
        message.add("app", appctx.getName());
        message.add("page", pagectx.getName());
        message.setSessionid(getComplexId(sessionid, logid));
        
        return (View)installapp.run(message);
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
        String sessionid = req.getSession().getId();
        
        req.setCharacterEncoding("UTF-8");
        
        if (apps.containsKey(sessionid)) {
            if (keepsession)
                pagectx = getPageContext(req, sessionid);
            logid = apps.get(sessionid).size();
        }
        
        if (pagectx == null) {
            contextdata = new ContextData();
            contextdata.sessionid = sessionid;
            contextdata.appname = INSTALLER;
            contextdata.pagename = stage.toString();
            contextdata.logid = logid;
            
            pagectx = createPageContext(contextdata);
        }
        
        if (pagectx.getViewData() != null) {
            iocaste = new Iocaste(this);
            pagectx = processController(iocaste, req, pagectx);
        }
        
        startRender(sessionid, resp, pagectx);
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
        logid = getLogid(pagetrack);
        
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
    public static final PageStackItem home(String sessionid) {
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
    public static final PageStackItem popPage(String sessionid) {
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
        Input inputdata;
        ControlComponent action;
        Enumeration<?> parameternames;
        PageContext pagectx_;
        Map<String, String[]> parameters;
        String appname, pagename, key, pagetrack = null, actionname = null;
        String sessionid = req.getSession().getId();
        
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

        if (parameters.containsKey("pagetrack")) {
            pagetrack = parameters.get("pagetrack")[0];
            parameters.remove("pagetrack");
        }
        
        config = new ControllerData();
        config.values = parameters;
        config.contextname = pagectx.getAppContext().getName();
        config.logid = getLogid(pagetrack);
        config.sessionid = getComplexId(sessionid, config.logid);
        config.state = new ViewState();
        config.state.view = pagectx.getViewData();
        config.pagectx = pagectx;
        callController(config);
        
        action = config.state.view.getElement(actionname);
        if (config.state.pagecall && (action == null ||
                !action.isCancellable() || action.allowStacking()))
            pushPage(config.sessionid, config.state.view.getAppName(),
                    config.state.view.getPageName());
        
        config.pagectx.inputs.clear();
        
        inputdata = new Input();
        inputdata.view = config.state.view;
        inputdata.container = null;
        inputdata.function = this;
        inputdata.register();
        
        updateView(config.sessionid, config.state.view, this);
        appname = config.state.rapp;
        if (appname == null)
            appname = pagectx.getAppContext().getName();
        
        pagename = config.state.rpage;
        if (pagename == null)
            pagename = pagectx.getName();
        
        pagectx.setError((byte)0);
        
        contextdata = new ContextData();
        contextdata.sessionid = sessionid;
        contextdata.appname = appname;
        contextdata.pagename = pagename;
        contextdata.logid = config.logid;
        
        pagectx_ = getPageContext(contextdata);
        if (pagectx_ == null)
            pagectx_ = createPageContext(contextdata);
        
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
        Input inputdata = new Input();
        
        inputdata.view = view;
        inputdata.container = null;
        inputdata.function = function;
        inputdata.register();
        
        appcontext.getPageContext(view.getPageName()).setViewData(view);
    }
}

class ContextData {
    public String sessionid;
    public String appname;
    public String pagename;
    public int logid;
}