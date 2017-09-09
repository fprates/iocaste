package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.HtmlTag;

public class HtmlTagRenderer extends AbstractElementRenderer<HtmlTag> {
    
    public HtmlTagRenderer(HtmlRenderer renderers) {
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
