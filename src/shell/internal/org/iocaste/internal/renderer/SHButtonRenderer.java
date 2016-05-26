package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.SearchHelp;

public class SHButtonRenderer extends Renderer {
    
    public static final XMLElement render(SearchHelp sh, Config config) {
        XMLElement linktag;
        String onclick, htmlname;
        
        htmlname = sh.getHtmlName();
        
        onclick = new StringBuilder("javascript:formSubmit('").
                append(config.getCurrentForm()).
                append("', '").append(config.getCurrentAction()).
                append("', '").append(htmlname).append("');").toString();
        linktag = new XMLElement("a");
        linktag.add("name", htmlname);
        linktag.add("href", onclick);
        linktag.addInner("values");
        return linktag;
    }
}
