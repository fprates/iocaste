package org.iocaste.office;

import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = -764098028292888271L;

    @Override
    protected void config() {
        register(new Services());
    }

}
