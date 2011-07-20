package org.iocaste.login;

import org.iocaste.protocol.Message;
import org.iocaste.protocol.ServerServlet;

public class LoginViewServlet extends ServerServlet {
    private static final long serialVersionUID = -8049483161856683488L;

    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.ServerServlet#config()
     */
    @Override
    protected void config() {
        register(new LoginView());
    }
    
    protected final void preRun(Message message) { };
}
