package org.iocaste.internal;

import org.iocaste.internal.renderer.Config;
import org.iocaste.internal.renderer.TextRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Text;

public class MessageRenderer {

    public static final XMLElement render(Const type, String message,
            Config config) {
        XMLElement xmlmsg = new XMLElement("div");
        Text text;
        
        xmlmsg.add("class", "message_box");
        if (message == null)
            return xmlmsg;
        
        text = new Text(null, "navbar.message");
        setMessage(text, (type == null)? Const.STATUS : type, message);
        xmlmsg.addChild(TextRenderer.render(text, config));
        return xmlmsg;
    }
    
    /**
     * 
     * @param text
     */
    private static final void setMessage(Text message, Const msgtype,
            String text) {
        message.setVisible(text != null);
        message.setText(text);
        
        switch (msgtype) {
        case ERROR:
            message.setStyleClass("error_message");
            break;
        case WARNING:
            message.setStyleClass("warning_message");
            break;
        case STATUS:
            message.setStyleClass("status_message");
            break;
        default:
            break;
        }
    }
}
