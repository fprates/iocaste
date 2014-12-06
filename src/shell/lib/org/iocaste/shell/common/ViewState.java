package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class ViewState implements Serializable {
    private static final long serialVersionUID = 3411135941186033095L;
    public View view;
    public String rapp, rpage, protocol, servername;
    public int port;
    public boolean keepview, reloadable, dontpushpage, pagecall, initialize;
    public Map<String, Object> parameters;
    public Set<String> initparams;
    public Map<String, String> headervalues;
}
