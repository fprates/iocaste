package org.iocaste.shell;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.View;

public class Services extends AbstractFunction {

    public Services() {
        export("style_constants_get", new GetStyleConstants());
        export("disconnect_event", new DisconnectEvent());
        export("get_pages_positions", "getPagesPositions");
        export("get_view", "getView");
        export("home", "home");
        export("http_req_process", PageRenderer.reqproc);
        export("login_app_get", "getLoginApp");
        export("pop_page", "popPage");
        export("push_page", "pushPage");
        export("set_pages_position", "setPagesPosition");
        export("update_view", "updateView");
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final String getLoginApp(Message message) {
        String sessionid = message.getSessionid();
        return PageRenderer.getLoginApp(sessionid);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final PageStackItem[] getPagesPositions(Message message) {
        String sessionid = message.getSessionid();
        return PageRenderer.reqproc.getPagesPositions(sessionid);
    }
    
    /**
     * Retorna a visão atual da sessão.
     * @param message:
     * - app_name (String): aplicação
     * - page_name (String): página
     * @return visão
     */
    public final View getView(Message message) {
        String sessionid = message.getSessionid();
        String appname = message.getst("app_name");
        String pagename = message.getst("page_name");
        
        return PageRenderer.getView(sessionid, appname, pagename);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final PageStackItem home(Message message) {
        String sessionid = message.getSessionid();
        String page = message.getst("page");
        return PageRenderer.home(sessionid, page);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final PageStackItem popPage(Message message) {
        String sessionid = message.getSessionid();
        
        return PageRenderer.popPage(sessionid);
    }
    
    /**
     * 
     * @param message
     */
    public final void pushPage(Message message) {
        String sessionid = message.getSessionid();
        View view = message.get("view");
        
        PageRenderer.pushPage(sessionid, view);
    }
    
    /**
     * 
     * @param message
     */
    public final void setPagesPosition(Message message) {
        String sessionid = message.getSessionid();
        String position = message.getst("position");
        
        PageRenderer.setPagesPosition(sessionid, position);
    }
    
    /**
     * 
     * @param message
     */
    public final void updateView(Message message) {
        PageRenderer.updateView(message.getSessionid(),
                (View)message.get("view"));
    }
}
