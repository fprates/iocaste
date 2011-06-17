package org.iocaste.protocol;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public abstract class IocasteModule implements Serializable {
    private static final long serialVersionUID = 4267708845753778441L;
    private HttpServletRequest req;
    
    /**
     * 
     * @param req
     */
    public final void setRequest(HttpServletRequest req) {
        this.req = req;
    }
    
    /**
     * 
     * @param url
     * @return
     * @throws IOException
     */
    protected final Service serviceInstance(String url) throws IOException {
        return new Service(req.getSession(false).getId(), url);
    }
    
}