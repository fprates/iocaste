package org.iocaste.shell;

import org.iocaste.protocol.InvalidSessionException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = -4447612067637162915L;

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
    protected final void preRun(Message message) throws Exception {
        try {
            super.preRun(message);
        } catch(InvalidSessionException e) {
            message.setId("return");
        }
    }
}
