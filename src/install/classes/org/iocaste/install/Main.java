package org.iocaste.install;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.internal.AbstractRenderer;
import org.iocaste.internal.AppContext;
import org.iocaste.internal.ContextData;
import org.iocaste.internal.Controller;
import org.iocaste.internal.ControllerData;
import org.iocaste.internal.InputStatus;
import org.iocaste.internal.PageContext;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class Main extends AbstractRenderer {
    private static final long serialVersionUID = -8316647525661973239L;
    private static final String INSTALLER = "iocaste-install";
    private Stages stage;
    private Function installapp;
    
    static {
        apps = new HashMap<>();
    }
    
    public Main() {
        stage = Stages.WELCOME;
        installapp = new Install();
    }
    
    @Override
    protected final void callController(ControllerData config) throws Exception
    {
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
        ContextData contextdata;
        int logid = 0;
        PageContext pagectx = null;
        String sessionid = req.getSession().getId();
        
        req.setCharacterEncoding("UTF-8");
        
        if (apps.containsKey(sessionid)) {
            if (keepsession)
                pagectx = getPageContext(req);
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
        
        if (pagectx.getViewData() != null)
            pagectx = processController(req, pagectx, sessionid);
        
        startRender(sessionid, resp, pagectx);
    }
    
    @Override
    protected boolean isConnected(ContextData ctxdata) {
        return false;
    }

    @Override
    protected boolean isExecuteAuthorized(String appname, String complexid) {
        return true;
    }
}