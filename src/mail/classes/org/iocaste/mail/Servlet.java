package org.iocaste.mail;

import org.iocaste.protocol.AbstractIocasteServlet;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = 7412355915013698299L;

    @Override
    protected void config() {
        register(new Services());
    }

}
