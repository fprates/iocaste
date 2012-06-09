package org.iocaste.shell;

import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.View;

public class Services extends AbstractFunction {

    public Services() {
        export("get_style_sheet", "getStyleSheet");
        export("get_view", "getView");
        export("home", "home");
        export("pop_page", "popPage");
        export("push_page", "pushPage");
        export("update_view", "updateView");
    }
    
    public final static Map<String, Map<String, String>> getStyle(
            String name, Function function) throws Exception {
        return Style.get(name, function);
    }
    
    public final Map<String, Map<String, String>> getStyleSheet(Message message)
            throws Exception {
        String sessionid = message.getSessionid();
        String appname = message.getString("appname");
        
        return PageRenderer.getStyleSheet(sessionid, appname);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final View getView(Message message) {
        String sessionid = message.getSessionid();
        String appname = message.getString("app_name");
        String pagename = message.getString("page_name");
        
        return PageRenderer.getView(sessionid, appname, pagename);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final String[] home(Message message) {
        String sessionid = message.getSessionid();
        
        return PageRenderer.home(sessionid);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final String[] popPage(Message message) {
        String sessionid = message.getSessionid();
        
        return PageRenderer.popPage(sessionid);
    }
    
    /**
     * 
     * @param message
     */
    public final void pushPage(Message message) {
        String sessionid = message.getSessionid();
        String appname = message.getString("app_name");
        String pagename = message.getString("page_name");
        
        PageRenderer.pushPage(sessionid, appname, pagename);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void updateView(Message message) throws Exception {
        PageRenderer.updateView(message.getSessionid(),
                (View)message.get("view"), this);
    }
}
