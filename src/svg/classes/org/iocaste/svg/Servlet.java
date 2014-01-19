package org.iocaste.svg;

import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = -63814252284153884L;

    @Override
    public final void config() {
        register(new Service());
    }
}
