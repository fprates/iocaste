package org.iocaste.protocol.user;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class UserProfile implements Serializable, Comparable<UserProfile> {
    private static final long serialVersionUID = -7925764805583958435L;
    private String name;
    private Set<Authorization> auths;
    
    public UserProfile(String name) {
        this.name = name;
        auths = new TreeSet<Authorization>();
    }
    
    public final void add(Authorization authorization) {
        auths.add(authorization);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public final int compareTo(UserProfile profile) {
        return name.compareTo(profile.getName());
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object object) {
        UserProfile profile;
        
        if (object == this)
            return true;
        
        if (!(object instanceof UserProfile))
            return false;
        
        profile = (UserProfile)object;
        return name.equals(profile.getName());
    }
    
    public final String getName() {
        return name;
    }
    
    public final Authorization[] getAuthorizations() {
        return auths.toArray(new Authorization[0]);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return name.hashCode();
    }
}
