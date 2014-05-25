package org.iocaste.examples;

import org.iocaste.protocol.AbstractIocasteServlet;

public class Servlet extends AbstractIocasteServlet{
    private static final long serialVersionUID = 7516342973531206442L;

    @Override
    protected void config() {
        register(new Service());
    }

}
