package org.iocaste.protocol.user;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = -6415620415288973044L;
    private String username;
    private String secret;
    
    /**
     * @return the name
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }
    
    /**
     * @param name the name to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @param secret the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
