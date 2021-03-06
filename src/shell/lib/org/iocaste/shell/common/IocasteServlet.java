package org.iocaste.shell.common;

import org.iocaste.protocol.AbstractIocasteServlet;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class IocasteServlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = 5705731493914203569L;
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#config()
     */
    @Override
    protected final void config() {
        setSingleton(false);
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
            throw new IocasteException(
                    "Servlet parameter \"%s\" not defined." +
                    " Define servlet parameter \"controller\"", type);
        
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
        String sessionid = message.getSessionid();
        
        if (isSessionRegistered(sessionid))
            return;
        
        function = initFunction("form");
        if (function == null)
            throw new Exception("Invalid form class.");

        register(sessionid, function);
    }
}
