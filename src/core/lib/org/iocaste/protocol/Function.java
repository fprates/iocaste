package org.iocaste.protocol;

import java.util.Set;

import javax.servlet.ServletContext;

public interface Function {
    public abstract <T extends Handler> T get(String handler);
    
    public abstract Set<String> getMethods();

    public abstract boolean isAuthorizedCall();
    
    public abstract Object run(Message message) throws Exception;
    
    public abstract void setAuthorizedCall(boolean authorized);
    
    public abstract void setServerName(String servername);
    
    public abstract void setServletContext(ServletContext context);
    
    public abstract Service serviceInstance(String path);
    
    public abstract void setSessionid(String sessionid);
}
