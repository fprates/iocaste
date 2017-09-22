package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.protocol.utils.XMLElement;

public class ContextMenuButtonRenderer {
    
    public static final XMLElement render(String htmlname, Config config,
            String text) {
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
        linktag.addInner(ContextMenu.getMessage(config, text));
        return linktag;
    }
}
