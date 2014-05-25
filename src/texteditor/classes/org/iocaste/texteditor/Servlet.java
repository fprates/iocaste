package org.iocaste.texteditor;

import org.iocaste.protocol.AbstractIocasteServlet;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = -5086958591255449413L;

    @Override
    protected void config() {
        register(new Services());
    }

}
