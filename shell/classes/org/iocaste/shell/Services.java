package org.iocaste.shell;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.ViewData;

public class Services extends AbstractFunction {

    public Services() {
        export("get_view", "getView");
    }
    
    public final ViewData getView(Message message) {
        return PageRenderer.getView(message.getSessionid(),
                message.getString("app_name"), message.getString("page_name"));
    }
}
