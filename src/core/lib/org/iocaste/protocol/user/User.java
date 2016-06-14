package org.iocaste.protocol.user;

import java.io.Serializable;

public class User implements Serializable, Comparable<User> {
    private static final long serialVersionUID = -6415620415288973044L;
    private String username, firstname, surname;

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public final int compareTo(User user) {
        return username.compareTo(user.getUsername());
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object object) {
        User user;
        
        if (object == this)
            return true;
        
        if (!(object instanceof User))
            return false;
        
        user = (User)object;
        if (!username.equals(user.getUsername()))
            return false;
        
        return true;
    }
    
    /**
     * Nome real do usuário.
     * @return
     */
    public final String getFirstname() {
        return firstname;
    }
    
    /**
     * Retorna sobrenome.
     * @return sobrenome
     */
    public final String getSurname() {
        return surname;
    }
    
    /**
     * Retorna nome do usuário.
     * @return nome
     */
    public final String getUsername() {
        return username;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return username.hashCode();
    }
    
    /**
     * Ajusta primeiro nome do usuário.
     * @param firstname primeiro nome
     */
    public final void setFirstname(String firstname) {
        this.firstname = firstname;
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
