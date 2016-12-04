package org.iocaste.appbuilder.common.portal;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PortalServlet extends HttpServlet {
	private static final long serialVersionUID = -2807094296176724556L;

    @Override
    public final void doGet(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        String[] params = {
            "login-manager",
            "start-page"
        };
        String value;
        StringBuilder url;
        ServletConfig config = getServletConfig();
    	
    	url = new StringBuilder("/iocaste-shell/?");
    	for (int i = 0; i < params.length; i++) {
            value = config.getInitParameter(params[i]);
    	    url.append(params[i]).append("=").append(value);
    	    if (i < (params.length - 1))
    	        url.append("&");
    	}
        resp.sendRedirect(url.toString());
    }
}
