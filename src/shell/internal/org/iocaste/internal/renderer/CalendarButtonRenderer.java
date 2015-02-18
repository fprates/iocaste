package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Calendar;

public class CalendarButtonRenderer extends Renderer {
    
    public static final XMLElement render(Calendar calendar, Config config) {
        XMLElement imgtag, linktag;
        String onclick, htmlname;
        
        imgtag = new XMLElement("img");
        imgtag.add("src", "/iocaste-shell/images/u160.png");
        imgtag.add("style", "width:85%; height:85%; vertical-align:middle");
        
        htmlname = calendar.getHtmlName();
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
