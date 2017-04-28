package org.iocaste.kernel.runtime.shell.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.HtmlTag;

public class HtmlTagRenderer extends AbstractElementRenderer<HtmlTag> {
    
    public HtmlTagRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.HTML_TAG);
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
