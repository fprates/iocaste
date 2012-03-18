package org.iocaste.shell;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.ViewData;

public class Services extends AbstractFunction {

    public Services() {
        export("get_view", "getView");
        export("update_view", "updateView");
        export("pop_page", "popPage");
        export("push_page", "pushPage");
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final ViewData getView(Message message) {
        String sessionid = message.getSessionid();
        String appname = message.getString("app_name");
        String pagename = message.getString("page_name");
        int logid = message.getInt("logid");
        
        return PageRenderer.getView(sessionid, appname, pagename, logid);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final String[] popPage(Message message) {
        String sessionid = message.getSessionid();
        int logid = message.getInt("logid");
        
        return PageRenderer.popPage(sessionid, logid);
    }
    
    /**
     * 
     * @param message
     */
    public final void pushPage(Message message) {
        String sessionid = message.getSessionid();
        String appname = message.getString("app_name");
        String pagename = message.getString("page_name");
        int logid = message.getInt("logid");
        
        PageRenderer.pushPage(sessionid, appname, pagename, logid);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void updateView(Message message) throws Exception {
        PageRenderer.updateView(message.getSessionid(),
                (ViewData)message.get("view"), this);
    }
}
