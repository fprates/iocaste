package org.iocaste.workbench;

import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = 1690661196652637609L;

    @Override
    protected void config() {
        register(new Services());
    }

}
