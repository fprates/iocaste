package org.iocaste.kernel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;

public class UserContext {
    private User user;
    private Locale locale;
    private int terminal;
    private Date conndate;
    private List<Authorization> authorizations;
    
    public UserContext(Locale locale) {
        this.locale = locale;
        conndate = Calendar.getInstance().getTime();
    }
    
    /**
     * 
     * @return
     */
    public final List<Authorization> getAuthorizations() {
        return authorizations;
    }
    
    /**
     * 
     * @return
     */
    public final Date getConnTime() {
        return conndate;
    }
    
    /**
     * 
     * @return
     */
    public final Locale getLocale() {
        return locale;
    }
    
    /**
     * 
     * @return
     */
    public final int getTerminal() {
        return terminal;
    }
    
    /**
     * 
     * @return
     */
    public final User getUser() {
        return user;
    }
    
    /**
     * 
     * @param authorizations
     */
    public final void setAuthorizations(List<Authorization> authorizations) {
        this.authorizations = authorizations;
    }
    
    /**
     * 
     * @param terminal
     */
    public final void setTerminal(int terminal) {
        this.terminal = terminal;
    }
    
    /**
     * 
     * @param user
     */
    public final void setUser(User user) {
        this.user = user;
    }
}
