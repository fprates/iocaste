package org.iocaste.kernel.common;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.iocaste.kernel.common.Function;
import org.iocaste.kernel.common.Message;
import org.iocaste.kernel.common.Service;

public abstract class AbstractFunction implements Function {
    private ServletContext context;
    private Map<String, String> exports;
    private Map<String, FunctionComponent> components;
    private String servername;
    private String sessionid;
    private boolean authorized;
    
    public AbstractFunction() {
        exports = new HashMap<>();
        components = new HashMap<>();
        exports.put("call_authorized", "callAuthorized");
        authorized = false;
    }
    
    /**
     * 
     * @param name
     * @param method
     */
    protected final void export(String name, String method) {
        exports.put(name, method);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#getMethods()
     */
    @Override
    public final Set<String> getMethods() {
        return exports.keySet();
    }
    
    /**
     * 
     * @param path
     * @return
     */
    public final String getRealPath(String path) {
        return context.getRealPath(path);
    }
    
    /**
     * 
     * @param path
     * @return
     */
    public final String getRealPath(String... path) {
        String separator = System.getProperty("file.separator");
        StringBuilder sb = new StringBuilder();
        
        for (String token : path)
            sb.append(separator).append(token);
        
        return getRealPath(sb.toString());
    }
    
    /**
     * 
     * @param path
     * @return
     */
    public final InputStream getResourceAsStream(String path) {
        return context.getResourceAsStream(path);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#isAuthorizedCall()
     */
    @Override
    public final boolean isAuthorizedCall() {
        return authorized;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#run(org.iocaste.protocol.Message)
     */
    @Override
    public final Object run(Message message) throws Exception {
        FunctionComponent component;
        Method method;
        String id = message.getId();
        String methodname = exports.get(id);
        
        if (methodname == null)
            throw new Exception(new StringBuilder("Method \"").
                    append(id).append("\" not implemented").toString());

        setSessionid(message.getSessionid());
        
        component = components.get(methodname);
        if (component != null) {
            return component.run(message);
        } else {
            method = getClass().getMethod(methodname, Message.class);
            return method.invoke(this, message);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#serviceInstance(java.lang.String)
     */
    @Override
    public final Service serviceInstance(String path) {
        String url = new StringBuffer(servername).append(path).toString();
        
        return new Service(sessionid, url);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setAuthorizedCall(boolean)
     */
    @Override
    public final void setAuthorizedCall(boolean authorized) {
        this.authorized = authorized;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setServerName(java.lang.String)
     */
    @Override
    public final void setServerName(String servername) {
        this.servername = servername;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setServletContext(
     *     javax.servlet.ServletContext)
     */
    @Override
    public final void setServletContext(ServletContext context) {
        this.context = context;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setSessionid(java.lang.String)
     */
    @Override
    public final void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

}
