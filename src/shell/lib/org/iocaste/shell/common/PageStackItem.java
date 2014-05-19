package org.iocaste.shell.common;

import java.io.Serializable;

public class PageStackItem implements Serializable {
    private static final long serialVersionUID = -5578481174763146570L;
    private String app, page, title;
    
    public PageStackItem(String app, String page) {
        this.app = app;
        this.page = page;
    }
    
    /**
     * @return o app
     */
    public final String getApp() {
        return app;
    }
    
    /**
     * @return o page
     */
    public final String getPage() {
        return page;
    }
    
    /**
     * @return o title
     */
    public final String getTitle() {
        return title;
    }
    
    /**
     * @param title o title a ser configurado
     */
    public final void setTitle(String title) {
        this.title = title;
    }
    
    
}
