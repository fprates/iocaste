package org.iocaste.globalconfig;

import org.iocaste.protocol.Message;
import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = -9220253306194832587L;

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#config()
     */
    @Override
    public void config() {
        register(new Services());
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#preRun(
     *     org.iocaste.protocol.Message)
     */
    @Override
    public void preRun(Message message) throws Exception {
        super.preRun(message);
    }
}
