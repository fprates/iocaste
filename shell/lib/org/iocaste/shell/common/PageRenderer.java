package org.iocaste.shell.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

public class PageRenderer extends HttpServlet {
    private static final long serialVersionUID = -8143025594178489781L;
    
    private final String getServerName(HttpServletRequest req) {
        return new StringBuffer(req.getScheme()).append("://").
                append(req.getServerName()).append(":").
                append(req.getServerPort()).toString();
    }
    
    private final void render(HttpServletResponse resp, String url, String page)
            throws Exception {
        ViewData vdata;
        Message message = new Message();
        PrintWriter writer = resp.getWriter();
        
        message.setId("get_view_data");
        message.add("page", page);
        vdata = (ViewData)Service.callServer(url, message);
        
        for (String line : vdata.getLines())
            writer.println(line);
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(
     *     javax.servlet.http.HttpServletRequest,
     *     javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String url = (String)req.getAttribute("url");
        String page = (String)req.getAttribute("page");

        if (url == null) {
            url = new StringBuffer(getServerName(req)).
                    append("/iocaste-login/view.html").toString();
            page = "authentic.html";
        } else {
            url = new StringBuffer(getServerName(req)).
                    append(url).toString();
        }
        
        try {
            render(resp, url, page);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
