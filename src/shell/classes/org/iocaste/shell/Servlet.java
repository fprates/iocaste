package org.iocaste.shell;

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
        
        authorize("update_view", null);
        authorize("process_inputs", null);
    }
}
