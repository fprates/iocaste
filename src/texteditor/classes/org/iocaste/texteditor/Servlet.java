package org.iocaste.texteditor;

import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = -5086958591255449413L;

    @Override
    protected void config() {
        register(new Services());
    }

}
