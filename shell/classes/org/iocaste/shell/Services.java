package org.iocaste.shell;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.ViewData;

public class Services extends AbstractFunction {

    public Services() {
        export("get_view", "getView");
        export("update_view", "updateView");
        export("pop_page", "popPage");
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final ViewData getView(Message message) {
        return PageRenderer.getView(message.getSessionid(),
                message.getString("app_name"), message.getString("page_name"));
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final String popPage(Message message) {
        return PageRenderer.popPage(message.getSessionid(),
                message.getString("app_name"));
    }
    
    /**
     * 
     * @param message
     */
    public final void updateView(Message message) {
        PageRenderer.updateView(message.getSessionid(),
                (ViewData)message.get("view"));
    }
}
