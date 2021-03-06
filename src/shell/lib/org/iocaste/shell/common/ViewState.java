package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ViewState implements Serializable {
    public static final int OK = 0;
    public static final int NOT_AUTHORIZED = 1;
    
    private static final long serialVersionUID = 3411135941186033095L;
    public View view;
    public String rapp, rpage, protocol, servername, messagetext, contenttype;
    public String contentencoding;
    public Object[] messageargs;
    public int port, error;
    public boolean keepview, reloadable, dontpushpage, pagecall, initialize;
    public boolean download;
    public Map<String, Object> parameters;
    public Set<String> initparams;
    public Map<String, String> headervalues;
    public Const messagetype;
    
    public ViewState() {
        headervalues = new HashMap<>();
        parameters = new HashMap<>();
    }
}
