package org.iocaste.docmanager;

import org.iocaste.protocol.Message;
import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = -2369348285176090474L;
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#config()
     */
    @Override
    public void config() {
        register(new Services());
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#preRun(
     *     org.iocaste.protocol.Message)
     */
    @Override
    public void preRun(Message message) throws Exception {
        super.preRun(message);
    }
}
