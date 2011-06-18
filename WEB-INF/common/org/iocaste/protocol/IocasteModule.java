package org.iocaste.protocol;

import javax.servlet.http.HttpServletRequest;

public abstract class IocasteModule implements Module {
    private static final long serialVersionUID = 4267708845753778441L;
    private HttpServletRequest req;
    
    /**
     * 
     * @param req
     */
    public final void setRequest(HttpServletRequest req) {
        this.req = req;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Module#serviceInstance(java.lang.String)
     */
    @Override
    public final Service serviceInstance(String url) throws Exception {
        return new Service(req.getSession(false).getId(), url);
    }
    
}