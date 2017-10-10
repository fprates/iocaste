package org.iocaste.runtime.common.application;

import java.util.Set;

import javax.servlet.ServletContext;

import org.iocaste.protocol.AbstractIocasteServlet;
import org.iocaste.protocol.Handler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.StandardService;
import org.iocaste.runtime.common.install.InstallContext;

public class ContextFunction<C extends Context> implements Application<C> {
    private String servletname, sessionid, servername;
    
    public ContextFunction(
            String servletname, String servername, String sessionid) {
        this.servletname = servletname;
        this.sessionid = sessionid;
        this.servername = servername;
    }

    @Override
    public C execute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends Handler> T get(String handler) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAppName() {
        return servletname;
    }

    @Override
    public Set<String> getMethods() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractIocasteServlet getServlet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void install(InstallContext installctx) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isAuthorizedCall() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object run(Message message) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Service serviceInstance(String path) {
        String url = new StringBuffer(servername).append(path).toString();
        return new StandardService(sessionid, url);
    }

    @Override
    public void set(AbstractIocasteServlet servlet) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setAuthorizedCall(boolean authorized) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setServletContext(ServletContext context) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setSessionid(String sessionid) {
        // TODO Auto-generated method stub
        
    }
}