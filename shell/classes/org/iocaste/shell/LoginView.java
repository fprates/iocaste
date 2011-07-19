package org.iocaste.shell;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

import org.iocaste.shell.common.ViewData;

public class LoginView extends AbstractFunction {

    public LoginView() {
        export("get_view_data", "getViewData");
    }
    
    public final ViewData getViewData(Message message) {
        ViewData vdata = new ViewData();
        
        vdata.add("<html>");
        vdata.add("<head>");
        vdata.add("<title>iocaste login</title>");
        vdata.add("</head>");
        vdata.add("<body>");
        vdata.add("<p>teste</p>");
        vdata.add("</body>");
        vdata.add("</html>");
        
        return vdata;
    }
}
