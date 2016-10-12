package org.iocaste.root;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main extends HttpServlet {
	private static final long serialVersionUID = 1338064531964353393L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		resp.sendRedirect("/iocaste-shell/index.html");
	}
}
