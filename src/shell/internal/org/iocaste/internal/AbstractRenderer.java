package org.iocaste.internal;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.View;

public abstract class AbstractRenderer extends HttpServlet implements Function {
    private static final String NOT_CONNECTED = "not.connected";
    private static final long serialVersionUID = -7711799346205632679L;
    private static final boolean NEW_SESSION = false;
    private static final boolean KEEP_SESSION = true;
    private static final String STD_CONTENT = "text/html";
    private String jsessionid, servername;
    protected String dbname;
    protected Map<String, Map<String, String>> style;
    protected MessageSource msgsource;
    
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
        String contenttype = pagectx.getViewData().getContentType();
        
        resp.setContentType((contenttype == null)? STD_CONTENT : contenttype);
        resp.setCharacterEncoding("UTF-8");
        
        if (pagectx.headervalues == null)
            return;
        
        for (String key : pagectx.headervalues.keySet())
            resp.setHeader(key, pagectx.headervalues.get(key));
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
        String complexid, appname, pagename;
        int logid;
        Input input;
        Message message;
        Map<String, Object> parameters;
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
        message.add("parameters", pagectx.parameters);
        message.setSessionid(complexid);
        
        if (pagectx.initparams != null) {
            parameters = new HashMap<>();
            for (String name : pagectx.initparams)
                parameters.put(name, pagectx.parameters.get(name));
            pagectx.initparams = null;
            pagectx.parameters = parameters;
        }
        
        try {
            service = new Service(complexid, composeUrl(appname));
            viewreturn = (Object[])service.call(message);
            
            view = (View)viewreturn[0];
            pagectx.headervalues = (Map<String, String>)viewreturn[1];
            
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
        servername = new StringBuffer(req.getScheme()).append("://").
                        append(req.getServerName()).append(":").
                        append(req.getServerPort()).toString();
        
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
        servername = new StringBuffer(req.getScheme()).append("://").
                        append(req.getServerName()).append(":").
                        append(req.getServerPort()).toString();
        
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
     * @return
     */
    protected final String getServerName() {
        return servername;
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
        
        if (view.getContentType() != null) {
            resp.reset();
            configResponse(resp, pagectx);
            os = resp.getOutputStream();
            bos = new BufferedOutputStream(os);
            
            content = view.getContent();
            if (content != null) {
                bos.write(content);
                resp.setContentLength(content.length);
            } else {
                for (String line : view.getPrintLines())
                    bos.write(line.getBytes());
            }
            
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
        
        return new Service(jsessionid, url);
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
        StyleSheet userstyle;
        String username, viewmessage;
        Const messagetype;
        AppContext appctx;
        View view;
        boolean hasrendered;

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
        renderer.setPageContext(pagectx);
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
        hasrendered = render(renderer, resp, pagectx, tracking);
        if (hasrendered)
            pagectx.setActions(renderer.getActions());
    }

}
