package org.iocaste.examples;

import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet{
    private static final long serialVersionUID = 7516342973531206442L;

    @Override
    protected void config() {
        register(new Service());
    }

}
