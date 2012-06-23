package org.iocaste.internal.renderer;

import org.iocaste.internal.XMLElement;
import org.iocaste.shell.common.HtmlTag;

public class HtmlTagRenderer extends Renderer {
    
    /**
     * 
     * @param htmltag
     * @return
     */
    public static final XMLElement render(HtmlTag htmltag) {
        XMLElement xmltag = new XMLElement(htmltag.getElement());
        
        xmltag.addInner(htmltag.getLines());
        
        return xmltag;
    }
}
