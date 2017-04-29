package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.HtmlTag;

public class HtmlTagRenderer extends AbstractElementRenderer<HtmlTag> {
    
    public HtmlTagRenderer(HtmlRenderer renderer) {
        super(renderer, Const.HTML_TAG);
    }

    /**
     * 
     * @param htmltag
     * @return
     */
    protected final XMLElement execute(HtmlTag htmltag, Config config) {
        XMLElement xmltag = new XMLElement(htmltag.getElement());
        
        xmltag.addInner(htmltag.getLines());
        
        return xmltag;
    }
}
