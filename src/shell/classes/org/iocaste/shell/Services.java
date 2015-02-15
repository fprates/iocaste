package org.iocaste.shell;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AccessTicket;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.View;

public class Services extends AbstractFunction {

    public Services() {
        export("add_ticket", "addTicket");
        export("get_pages_positions", "getPagesPositions");
        export("get_view", "getView");
        export("home", "home");
        export("pop_page", "popPage");
        export("push_page", "pushPage");
        export("remove_ticket", "removeTicket");
        export("set_pages_position", "setPagesPosition");
        export("style_remove", "removeStyle");
        export("style_save", "saveStyle");
        export("update_view", "updateView");
    }
    
    /**
     * Adiciona tíquete de acesso à lista de iocaste-shell.
     * @param message:
     * - ticket (TicketData): tíquete de acesso
     * @return código do tíquete
     */
    public final String addTicket(Message message) {
        AccessTicket ticket = message.get("ticket");
        return PageRenderer.addTicket(this, ticket);
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
        String appname = message.getString("app_name");
        String pagename = message.getString("page_name");
        
        return PageRenderer.getView(sessionid, appname, pagename);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final PageStackItem[] getPagesPositions(Message message) {
        String sessionid = message.getSessionid();
        return PageRenderer.getPagesPositions(sessionid);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final PageStackItem home(Message message) {
        String sessionid = message.getSessionid();
        
        return PageRenderer.home(sessionid);
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
        String appname = message.getString("app_name");
        String pagename = message.getString("page_name");
        
        PageRenderer.pushPage(sessionid, appname, pagename);
    }
    
    public final void removeStyle(Message message) {
        String name = message.getString("name");
        StyleServices.remove(this, name);
    }
    
    /**
     * 
     * @param message
     */
    public final void removeTicket(Message message) {
        String ticket = message.getString("ticket");
        
        PageRenderer.removeTicket(ticket, this);
    }
    
    public final void saveStyle(Message message) {
        String name = message.get("name");
        StyleSheet stylesheet = message.get("style");
        
        StyleServices.save(this, name, stylesheet);
    }
    
    /**
     * 
     * @param message
     */
    public final void setPagesPosition(Message message) {
        String sessionid = message.getSessionid();
        String position = message.getString("position");
        
        PageRenderer.setPagesPosition(sessionid, position);
    }
    
    /**
     * 
     * @param message
     */
    public final void updateView(Message message) {
        PageRenderer.updateView(message.getSessionid(),
                (View)message.get("view"), this);
    }
}
