package org.iocaste.shell;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.internal.AbstractRenderer;
import org.iocaste.internal.AppContext;
import org.iocaste.internal.Common;
import org.iocaste.internal.ContextData;
import org.iocaste.internal.Controller;
import org.iocaste.internal.InputStatus;
import org.iocaste.internal.ControllerData;
import org.iocaste.internal.PageContext;
import org.iocaste.internal.SessionContext;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.AccessTicket;
import org.iocaste.shell.common.PopupControl;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class PageRenderer extends AbstractRenderer {
    private static final long serialVersionUID = -8143025594178489781L;
    private static final String EXCEPTION_HANDLER = "iocaste-exhandler";
    private static TicketControl tickets;
    
    static {
        tickets = new TicketControl();
    }
    
    public PageRenderer() {
        msgsource = Messages.getMessages();
    }
    
    public static final String addTicket(Function function, AccessTicket ticket)
    {
        return tickets.add(ticket, function);
    }
    
    @Override
    protected final void callController(ControllerData config) throws Exception
    {
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
        if ((control != null) && control.isPopup()) {
            config.popupcontrol = (PopupControl)control;
            config.contexturl = composeUrl(
                    config.popupcontrol.getApplication());
            return;
        }
        
        try {
            service = new Service(config.sessionid,
                    composeUrl(config.contextname));
            
            config.state = (ViewState)service.call(message);
            if (config.state.messagetype == Const.ERROR)
                Common.rollback(getServerName(), config.sessionid);
            else
                Common.commit(getServerName(), config.sessionid);
        } catch (Exception e) {
            if (getPagesPositions(config.sessionid).length == 1)
                pushPage(config.sessionid, config.state.view.getAppName(),
                        config.state.view.getPageName());
            
            Common.rollback(getServerName(), config.sessionid);
            throw e;
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
        pagectx.parameters.put("exception", exception);
        pagectx.parameters.put("exview", expagectx.getViewData());
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
        SessionContext sessionctx;
        PageContext pagectx;
        ContextData contextdata = new ContextData();
        String login = req.getParameter("login-manager");
        
        contextdata.appname = (login == null)? "iocaste-login" : login;
        contextdata.sessionid = sessionid;
        contextdata.pagename = "authentic";
        contextdata.logid = logid;
        contextdata.initialize = true;
        
        pagectx = createPageContext(contextdata);
        sessionctx = apps.get(sessionid).get(logid);
        sessionctx.loginapp = new StringBuilder(contextdata.appname).
            append(".").append(contextdata.pagename).toString();
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
        pagectx.parameters.put("username", ticket.getUsername());
        pagectx.parameters.put("secret", ticket.getSecret());
        pagectx.parameters.put("locale", ticket.getLocale());
        pagectx.parameters.put("ticket", ticketcode);
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
        
        if (hostname == null) {
            hostname = req.getServerName();
            protocol = req.getScheme();
            port = req.getServerPort();
        }
        
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
     * @param sessionid
     * @return
     */
    public static final String getLoginApp(String sessionid) {
        String[] complexid = sessionid.split(":");
        int logid = Integer.parseInt(complexid[1]);
        
        return apps.get(complexid[0]).get(logid).loginapp;
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
}