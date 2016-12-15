package org.iocaste.runtime.common;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Handler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.StandardService;
import org.iocaste.shell.common.IocasteServlet;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.View;

public class ApplicationServlet extends IocasteServlet implements Function {
    private static final long serialVersionUID = 606195820570774650L;
    private String jsessionid, servername;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        GenericService service;
        Message message;
        Map<String, String[]> reqparameters;
        Object[][] parameters;
        View view;
        int i = 0;
        message = new Message("http_req_process");
        reqparameters = req.getParameterMap();
        parameters = new Object[reqparameters.size()][2];
        for (String key : reqparameters.keySet()) {
            parameters[i][0] = key;
            parameters[i++][1] = reqparameters.get(key);
        }
        message.add("parameters", parameters);

        jsessionid = req.getSession().getId();
        if (servername == null)
            servername = new StringBuffer(req.getScheme()).
                append("://127.0.0.1:").append(req.getLocalPort()).toString();
        service = new GenericService(this, Shell.SERVER_NAME);
        view = service.invoke(message);
    }

    @Override
    public <T extends Handler> T get(String handler) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> getMethods() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAuthorizedCall() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object run(Message message) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAuthorizedCall(boolean authorized) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setServerName(String servername) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setServletContext(ServletContext context) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Service serviceInstance(String path) {
        String url = new StringBuffer(servername).append(path).toString();
        
        return new StandardService(jsessionid, url);
    }

    @Override
    public void setSessionid(String sessionid) {
        // TODO Auto-generated method stub
        
    }

}
