package org.iocaste.copy;

import org.iocaste.protocol.AbstractIocasteServlet;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = -6927831841900443317L;

    @Override
    protected void config() {
        register(new Service());
    }

}
