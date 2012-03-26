package org.iocaste.login;

public class Login {
    private String username;
    private String secret;
    private String locale;
    
    public final String getLocale() {
        return locale;
    }
    
    /**
     * @return the username
     */
    public final String getUsername() {
        return username;
    }
    
    /**
     * @return the secret
     */
    public final String getSecret() {
        return secret;
    }
    
    /**
     * 
     * @param locale
     */
    public final void setLocale(String locale) {
        this.locale = locale;
    }
    
    /**
     * @param username the username to set
     */
    public final void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @param secret the secret to set
     */
    public final void setSecret(String secret) {
        this.secret = secret;
    }
    
}
