package org.iocaste.shell;

import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.ClientForm;

public class LoginForm extends ClientForm {
    private static final long serialVersionUID = 794315365771230508L;
    private String user;
    private String secret;
    
    /*
     * 
     * Getters
     * 
     */
    
    /**
     * @return the user
     */
    public final String getUser() {
        return user;
    }
    
    /**
     * @return the secret
     */
    public final String getSecret() {
        return secret;
    }
    
    /*
     * 
     * Setters
     * 
     */
    
    /**
     * @param user the user to set
     */
    public final void setUser(String user) {
        this.user = user;
    }
    
    /**
     * @param secret the secret to set
     */
    public final void setSecret(String secret) {
        this.secret = secret;
    }
    
    /*
     * 
     * Controller
     * 
     */
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public final String autenticate() throws Exception {
        Iocaste iocaste = new Iocaste(this);
        
        if (iocaste.login(user, secret))
            return "tasksel";
        else
            message(ERROR, "invalid.login");
        
        return null;
    }
}
