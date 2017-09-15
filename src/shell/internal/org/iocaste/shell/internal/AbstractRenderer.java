package org.iocaste.shell.internal;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.protocol.AbstractIocasteServlet;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Handler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.StandardService;
import org.iocaste.shell.common.AccessTicket;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.View;

public abstract class AbstractRenderer extends HttpServlet
        implements Function {
    private static final long serialVersionUID = -7711799346205632679L;
    private static final boolean NEW_SESSION = false;
    private static final boolean KEEP_SESSION = true;
    private String jsessionid, servername;
    public static ProcessHttpRequisition reqproc;
    
    public static final String addTicket(Function function, AccessTicket ticket)
    {
        return reqproc.tickets.add(ticket, function);
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
    
    protected void entry(HttpServletRequest req, HttpServletResponse resp,
            boolean keepsession) throws Exception {
        RendererContext context;
        
        req.setCharacterEncoding("UTF-8");
        
        context = new RendererContext();
        context.sessionid = req.getSession().getId();
        context.keepsession = keepsession;
        context.req = req;
        context.resp = resp;
        context.hostname = req.getServerName();
        context.protocol = req.getScheme();
        context.port = req.getServerPort();
        reqproc.setFunction(this);
        reqproc.run(context);
    }

    @Override
    public final <T extends Handler> T get(String handler) {
        return null;
    }
    
    /**
     * 
     * @param sessionid
     * @return
     */
    public static final String getLoginApp(String sessionid) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return reqproc.apps.get(complexid[0]).get(logid).loginapp;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#getMethods()
     */
    @Override
    public final Set<String> getMethods() {
        return null;
    }
    
    public final String getServerName() {
        return servername;
    }
    
    @Override
    public final AbstractIocasteServlet getServlet() {
        return null;
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
        
        AppContext appcontext = reqproc.apps.get(complexid[0]).get(logid).
                getAppContext(appname);
        
        return appcontext.getPageContext(pagename).getViewData();
    }
    
    /**
     * 
     * @param getSessionId()
     * @return
     */
    public static final PageStackItem home(String sessionid, String page) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return reqproc.apps.get(complexid[0]).get(logid).home(page);
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
     * @param getSessionId()
     * @param logid
     * @return
     */
    public static final PageStackItem popPage(String sessionid) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return reqproc.apps.get(complexid[0]).get(logid).popPage();
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
        List<SessionContext> sessionsctx = reqproc.apps.get(complexid[0]);
        
        if (sessionsctx == null)
            return;
        
        sessionsctx.get(logid).pushPage(view);
    }
    
    public static final void removeAppEntry(String sessionid) {
        String[] complexid = sessionid.split(":");
        reqproc.apps.remove(complexid[0]);
    }
    
    /**
     * 
     * @param ticketcode
     * @param function
     */
    public static final void removeTicket(String ticketcode, Function function)
    {
        reqproc.tickets.remove(ticketcode, function);
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#run(org.iocaste.protocol.Message)
     */
    @Override
    public final Object run(Message message) throws Exception {
        return null;
    }

    @Override
    public final void set(AbstractIocasteServlet servlet) { }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setAuthorizedCall(boolean)
     */
    @Override
    public final void setAuthorizedCall(boolean authorized) { }
    
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
        
        context = reqproc.apps.get(complexid[0]).get(logid);
        composition = position.split("\\.");
        context.setPagesPosition(composition[0], composition[1]);
    }

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
        List<SessionContext> sessionctx = reqproc.apps.get(complexid[0]);
        
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
