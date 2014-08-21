package org.iocaste.kernel;


import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.common.AbstractIocasteServlet;
import org.iocaste.kernel.common.IocasteException;
import org.iocaste.kernel.common.Message;
import org.iocaste.kernel.database.Database;

public class Servlet extends AbstractIocasteServlet {
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
        register(new Database());
//        register(new Login());
//        register(new Users());
//        register(new Authorization());
//        register(new Documents());
        
        
        authorize("is_connected", null);
        authorize("login", null);
        
        parameters = new HashMap<String, Object[]>();
        parameters.put("from",
                new String[] {"SHELL001", "SHELL002", "SHELL003", "SHELL004"});
        authorize("checked_select", parameters);
        
        authorize("get_host", null);
        authorize("get_locale", null);
        authorize("commit", null);
        authorize("rollback", null);
        authorize("is_authorized", null);
        
        parameters = new HashMap<String, Object[]>();
        parameters.put("parameter", new String[] {"dbname"});
        authorize("get_system_parameter", parameters);
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
        
        services.setHost(getServerName(message.getSessionid()));
        
        if (isAuthorized(message))
            return;
        
        if (!services.isConnected(message))
            throw new IocasteException(new StringBuilder(message.getId()).
                    append("() denied: invalid session.").toString());
    }
}
