package org.iocaste.shell.common;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.ServerServlet;

public class IocasteServlet extends ServerServlet {
    private static final long serialVersionUID = 5705731493914203569L;
    private boolean initialized;

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#config()
     */
    @Override
    protected final void config() {
        initialized = false;
    }
    
    /**
     * 
     * @param type
     * @return
     * @throws Exception
     */
    private final Function initFunction(String type) throws Exception {
        String name = getServletConfig().getInitParameter(type);
        
        if (name == null)
            throw new Exception("Servlet parameter \""+type+"\" not defined." +
                    " Define servlet parameter \"controller\"");
        
        return (Function)Class.forName(name).newInstance();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#preRun(
     *     org.iocaste.protocol.Message)
     */
    @Override
    protected final void preRun(Message message) throws Exception {
        Function function;
        
        if (initialized)
            return;
        
        function = initFunction("form");
        
        if (function == null)
            throw new Exception("Invalid form class.");

        register(function);
        initialized = true;
    }
}
