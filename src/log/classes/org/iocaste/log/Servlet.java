package org.iocaste.log;

import org.iocaste.protocol.AbstractIocasteServlet;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = 7840007824883969898L;

    protected void config() {
        register(new Services());
        authorize("commit", null);
    }
}
