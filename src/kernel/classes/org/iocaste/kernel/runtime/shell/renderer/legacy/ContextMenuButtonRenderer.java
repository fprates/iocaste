package org.iocaste.kernel.runtime.shell.renderer.legacy;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.protocol.utils.XMLElement;

public class ContextMenuButtonRenderer {
    
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
        if (messages != null)
            linktag.addInner(
                    messages.containsKey(text)? messages.get(text) : text);
        else
            linktag.addInner(text);
        return linktag;
    }
}
