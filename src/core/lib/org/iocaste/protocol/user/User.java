package org.iocaste.protocol.user;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = -6415620415288973044L;
    private String username;
    private String firstname;
    private String surname;
    private String secret;
    
    /**
     * Nome real do usuário.
     * @return
     */
    public String getFirstname() {
        return firstname;
    }
    
    /**
     * Retorna senha criptografada.
     * @return senha
     */
    public String getSecret() {
        return secret;
    }
    
    /**
     * Retorna sobrenome.
     * @return sobrenome
     */
    public String getSurname() {
        return surname;
    }
    
    /**
     * Retorna nome do usuário.
     * @return nome
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Ajusta primeiro nome do usuário.
     * @param firstname primeiro nome
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    /**
     * Ajusta senha.
     * @param secret senha
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }
    
    /**
     * Ajusta sobrenome.
     * @param surname sobrenome
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    /**
     * Ajusta nome do usuário.
     * @param name nome
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
