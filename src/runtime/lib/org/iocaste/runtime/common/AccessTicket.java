package org.iocaste.runtime.common;

import java.io.Serializable;

public class AccessTicket implements Serializable {
    private static final long serialVersionUID = 2546685357828782530L;
    private String appname, pagename, username, secret, locale;

    public final String getAppname() {
        return appname;
    }

    public final String getLocale() {
        return locale;
    }
    
    public final String getPagename() {
        return pagename;
    }
    
    public final String getSecret() {
        return secret;
    }

    public final String getUsername() {
        return username;
    }
    
    public final void setAppname(String appname) {
        this.appname = appname;
    }

    public final void setLocale(String locale) {
        this.locale = locale;
    }

    public final void setPagename(String pagename) {
        this.pagename = pagename;
    }
    
    public final void setSecret(String secret) {
        this.secret = secret;
    }

    public final void setUsername(String username) {
        this.username = username;
    }
    
}
