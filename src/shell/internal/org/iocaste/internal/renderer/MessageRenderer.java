package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.internal.PageContext;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Text;

public class MessageRenderer extends AbstractElementRenderer<Component> {
    public PageContext pagectx;
    
    public MessageRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.MESSAGE);
    }

    @Override
    protected final XMLElement execute(Component component, Config config) {
        Text text;
        XMLElement xmlmsg = new XMLElement("div");
        
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
        xmlmsg.addChild(get(Const.TEXT).run(text, config));
        return xmlmsg;
    }
    
    /**
     * 
     * @param text
     */
    private final void setMessage(Text message, Const msgtype,
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
