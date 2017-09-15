package org.iocaste.shell.internal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RendererContext {
    public String sessionid, hostname, protocol;
    public boolean keepsession;
    public int port;
    public HttpServletRequest req;
    public HttpServletResponse resp;
    public Object[][] parameters;
}
