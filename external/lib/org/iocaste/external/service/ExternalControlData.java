package org.iocaste.external.service;

public class ExternalControlData {
    private String action;
    private String[] destiny;
    
    public ExternalControlData() {
        destiny = new String[2];
    }
    public final String getAction() {
        return action;
    }
    
    public final String[] getDestiny() {
        return destiny;
    }
    
    public final void redirect(String appname, String viewname) {
        destiny[0] = appname;
        destiny[1] = viewname;
    }
    
    public final void setAction(String action) {
        this.action = action;
    }
}
