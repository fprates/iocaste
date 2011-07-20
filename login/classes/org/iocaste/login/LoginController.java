package org.iocaste.login;

import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractController;

public class LoginController extends AbstractController {

    @Override
    protected String entry(String action) throws Exception {
        Iocaste iocaste = new Iocaste(this);
        String username = getString("username");
        String secret = getString("secret");
        
        if (iocaste.login(username, secret))
            System.out.println("Connected");
        else
            System.out.println("Disconnected");
        
        return null;
    }

    
}
