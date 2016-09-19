package org.iocaste.appbuilder.common.portal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PortalServlet extends HttpServlet {
	private static final long serialVersionUID = -2807094296176724556L;

    @Override
    public final void doGet(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
    	String loginman = getServletConfig().getInitParameter("login_manager");
        resp.sendRedirect(String.format("/iocaste-shell/?login-manager=%s", loginman));
    }
}
