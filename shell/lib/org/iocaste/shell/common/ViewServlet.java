package org.iocaste.shell.common;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.ServerServlet;

public class ViewServlet extends ServerServlet {
    private static final long serialVersionUID = 5705731493914203569L;
    private Class<?> function;

    @Override
    protected void config() {
        function = null;
    }
    
    @Override
    protected final void preRun(Message message) throws Exception {
        String classname;
        
        if (function != null)
            return;
        
        classname = getServletConfig().getInitParameter("view_class");
        if (classname == null)
            throw new Exception("View class not defined." +
            		" Define servlet parameter \"view_class\"");
        
        function = Class.forName(classname);
        
        if (function == null)
            throw new Exception("View class \""+classname+"\" invalid.");
        
        register((Function)function.newInstance());
    }

}
