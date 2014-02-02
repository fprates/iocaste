package org.iocaste.shell;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AccessTicket;
import org.iocaste.shell.common.View;

public class Services extends AbstractFunction {

    public Services() {
        export("add_ticket", "addTicket");
        export("get_view", "getView");
        export("home", "home");
        export("pop_page", "popPage");
        export("push_page", "pushPage");
        export("remove_ticket", "removeTicket");
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
     */
    public final void removeTicket(Message message) {
        String ticket = message.getString("ticket");
        
        PageRenderer.removeTicket(ticket, this);
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
