package org.iocaste.shell;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.ViewData;


public class PageRenderer extends HttpServlet {
    private static final long serialVersionUID = -8143025594178489781L;
    private String url;
    private String page;
    
    public PageRenderer() {
        url = null;
        page = null;
    }
    
    /**
     * 
     * @param req
     * @param url
     * @return
     * @throws Exception
     */
    private final ControlData callController(
            HttpServletRequest req, String url) throws Exception {
        String paramname;
        Message message = new Message();
        
        message.setId("exec_action");
        
        for (Object obj : req.getParameterMap().keySet()) {
            paramname = (String)obj;
            message.add(paramname, req.getParameter(paramname));
        }
            
        return (ControlData)Service.callServer(url, message);
    }
    
    /**
     * 
     * @param req
     * @return
     */
    private final String getServerName(HttpServletRequest req) {
        return new StringBuffer(req.getScheme()).append("://").
                append(req.getServerName()).append(":").
                append(req.getServerPort()).toString();
    }
    
    /**
     * 
     * @param resp
     * @param url
     * @param page
     * @throws Exception
     */
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
        ControlData controldata;
        String action = req.getParameter("action");
        
        try {
            if (action != null) {
                controldata = callController(req, url);
                page = controldata.getPageRedirect();
            }
            
            if (page != null)
                url = new StringBuffer(getServerName(req)).
                        append(page).toString();
            
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
        if (url == null) {
            url = new StringBuffer(getServerName(req)).
                    append("/iocaste-login/view.html").toString();
            page = "authentic.html";
        }
        
        try {
            render(resp, url, page);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
