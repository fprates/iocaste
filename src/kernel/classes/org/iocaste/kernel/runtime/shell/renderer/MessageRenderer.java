package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.tooldata.Text;
import org.iocaste.shell.common.tooldata.ToolData;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

public class MessageRenderer extends AbstractElementRenderer<Component> {
    
    public MessageRenderer(HtmlRenderer renderer) {
        super(renderer, Const.MESSAGE);
    }

    @Override
    protected final XMLElement execute(Component component, Config config)
            throws Exception {
    	ToolData tooldata;
        Text text;
        XMLElement xmlmsg = new XMLElement("div");
        
        xmlmsg.add("class", "message_box");
        if (config.viewctx.messagetext == null) {
            xmlmsg.addInner("");
            return xmlmsg;
        }

        tooldata = new ToolData(TYPES.TEXT);
        tooldata.name = "navbar.message";
        text = new Text(config.viewctx, tooldata);
        setMessage(text, (config.viewctx.messagetype == null)?
                Const.STATUS : config.viewctx.messagetype,
                config.viewctx.messagetext,
                config.viewctx.messageargs);
        xmlmsg.addChild(get(Const.TEXT).run(text, config));
        return xmlmsg;
    }
    
    /**
     * 
     * @param text
     */
    private final void setMessage(Text message, Const msgtype,
            String text, Object[] args) {
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
