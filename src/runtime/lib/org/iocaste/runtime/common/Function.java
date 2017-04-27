package org.iocaste.runtime.common;

import java.util.Set;

import javax.servlet.ServletContext;

import org.iocaste.protocol.Message;

public interface Function {
    
    public abstract <T extends Handler> T get(String handler);
    
    public abstract Set<String> getMethods();

    public abstract boolean isAuthorizedCall();
    
    public abstract Object run(Message message) throws Exception;
    
    public abstract void setAuthorizedCall(boolean authorized);
    
    public abstract void setServletContext(ServletContext context);
}
