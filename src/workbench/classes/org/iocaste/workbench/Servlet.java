package org.iocaste.workbench;

import org.iocaste.protocol.AbstractIocasteServlet;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = 1690661196652637609L;

    @Override
    protected void config() {
        register(new Services());
    }

}
