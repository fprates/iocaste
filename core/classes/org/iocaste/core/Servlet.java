package org.iocaste.core;


import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = -8569034003940826582L;
    private Services services;
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#config()
     */
    @Override
    public void config() {
        Map<String, Object[]> parameters;
        
        services = new Services();
        register(services);
        
        authorize("is_connected", null);
        authorize("login", null);
        
        parameters = new HashMap<String, Object[]>();
        parameters.put("from",
                new String[] {"shell001", "shell002", "shell003"});
        authorize("checked_select", parameters);
        authorize("get_host", null);
        authorize("get_locale", null);
        authorize("commit", null);
        authorize("rollback", null);
        authorize("is_authorized", null);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#preRun(
     *     org.iocaste.protocol.Message)
     */
    protected final void preRun(Message message) throws Exception {
        if (!services.isInitialized()) {
            services.setServletContext(getServletContext());
            services.init();
        }
        
        services.setHost(getServerName());
        
        if (isAuthorized(message))
            return;
        
        if (!services.isConnected(message))
            throw new IocasteException(new StringBuilder(message.getId()).
                    append("() denied: invalid session.").toString());
    }
}
