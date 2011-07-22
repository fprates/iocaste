package org.iocaste.login;

import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractController;
import org.iocaste.shell.common.MessageType;

public class LoginController extends AbstractController {

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractController#entry(java.lang.String)
     */
    @Override
    protected final void entry(String action) throws Exception {
        Iocaste iocaste = new Iocaste(this);
        String username = getString("username");
        String secret = getString("secret");
        
        if (iocaste.login(username, secret))
            redirect("/iocaste-tasksel/view.html");
        else
            message(MessageType.ERROR, "invalid.login");
    }

    
}
