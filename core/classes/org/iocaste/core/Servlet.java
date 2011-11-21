package org.iocaste.core;


import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = -8569034003940826582L;
    private Services services;
    
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
    }
}
