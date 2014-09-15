package org.template;

import org.iocaste.protocol.AbstractIocasteServlet;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = -4447612067637162915L;

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#config()
     * 
     * registre as bibliotecas aqui com register()
     */
    @Override
    public void config() {
        register(new Services());
    }
}
