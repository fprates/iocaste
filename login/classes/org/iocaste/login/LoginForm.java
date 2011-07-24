package org.iocaste.login;

import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.MessageType;

public class LoginForm extends AbstractForm {

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractForm#entry(java.lang.String)
     */
    @Override
    protected final void entry(String action) throws Exception {
        Iocaste iocaste = new Iocaste(this);
        String username = getString("username");
        String secret = getString("secret");
        
        if (iocaste.login(username, secret))
            redirect("iocaste-tasksel", null);
        else
            message(MessageType.ERROR, "invalid.login");
    }

    
}
