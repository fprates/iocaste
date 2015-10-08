package org.iocaste.internal;

import org.iocaste.internal.renderer.Config;
import org.iocaste.internal.renderer.TextRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Text;

public class MessageRenderer {

    public static final XMLElement render(PageContext pagectx, Config config) {
        XMLElement xmlmsg = new XMLElement("div");
        Text text;
        
        xmlmsg.add("class", "message_box");
        if (pagectx.messagetext == null) {
            xmlmsg.addInner("");
            return xmlmsg;
        }
        
        text = new Text(config.getView(), "navbar.message");
        setMessage(text, (pagectx.messagetype == null)?
                Const.STATUS : pagectx.messagetype,
                pagectx.messagetext,
                pagectx.messageargs,
                config);
        xmlmsg.addChild(TextRenderer.render(text, config));
        return xmlmsg;
    }
    
    /**
     * 
     * @param text
     */
    private static final void setMessage(Text message, Const msgtype,
            String text, Object[] args, Config config) {
        String formatted;
        
        message.setVisible(text != null);
        if (args == null) {
            message.setText(text);
        } else {
            formatted = String.format(text, args);
            message.setText(formatted);
            message.setTranslatable(false);
        }
        
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
