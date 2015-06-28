package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.SearchHelp;

public class SHButtonRenderer extends Renderer {
    
    public static final XMLElement render(SearchHelp sh, Config config) {
        XMLElement imgtag, linktag;
        String onclick, htmlname;
        
        imgtag = new XMLElement("img");
        imgtag.add("src", "/iocaste-shell/images/u6.png");
        imgtag.add("style", "width:15px; height:15px; vertical-align:middle;"
                + "float:right");
        
        htmlname = sh.getHtmlName();
        onclick = new StringBuilder("javascript:formSubmit('").
                append(config.getCurrentForm()).
                append("', '").append(config.getCurrentAction()).
                append("', '").append(htmlname).append("');").toString();
        
        linktag = new XMLElement("a");
        linktag.add("name", htmlname);
        linktag.add("href", onclick);
        linktag.addChild(imgtag);
        
        return linktag;
    }
}
