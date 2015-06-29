package org.iocaste.transport;

import org.iocaste.protocol.AbstractIocasteServlet;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void config() {
        register(new Services());
    }

}
