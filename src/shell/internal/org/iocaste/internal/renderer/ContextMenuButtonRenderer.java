package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;

public class ContextMenuButtonRenderer extends Renderer {
    
    public static final XMLElement render(String htmlname, Config config,
            Map<String, String> messages, String text) {
        XMLElement linktag;
        String onclick;
        
        onclick = new StringBuilder("javascript:formSubmit('").
                append(config.getCurrentForm()).
                append("', '").append(config.getCurrentAction()).
                append("', '").append(htmlname).append("');").toString();
        linktag = new XMLElement("a");
        linktag.add("name", htmlname);
        linktag.add("href", onclick);
        linktag.add("class", "ctxmenu_link");
        linktag.addInner((messages != null)? messages.get(text) : text);
        return linktag;
    }
}
