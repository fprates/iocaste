package org.iocaste.shell;

import org.iocaste.protocol.Message;
import org.iocaste.protocol.ServerServlet;

public class LoginViewServlet extends ServerServlet {
    private static final long serialVersionUID = -8049483161856683488L;

    @Override
    protected void config() {
        register(new LoginView());
    }
    
    protected final void preRun(Message message) { };
}
