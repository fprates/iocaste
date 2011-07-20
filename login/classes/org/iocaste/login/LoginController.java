package org.iocaste.login;

import org.iocaste.shell.common.AbstractController;

public class LoginController extends AbstractController {

    @Override
    protected String entry(String action) {
        System.out.println(getString("username"));
        System.out.println(getString("secret"));
        
        return null;
    }

    
}
