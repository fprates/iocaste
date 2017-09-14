package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Frame;

public class FrameRenderer extends AbstractElementRenderer<Frame> {
    
    public FrameRenderer(HtmlRenderer renderers) {
        super(renderers, Const.FRAME);
    }

    /**
     * 
     * @param frame
     * @param config
     * @return
     */
    protected final XMLElement execute(Frame frame, Config config)
            throws Exception {
        XMLElement frametag, legendtag;
        String text;
        
        if (frame.isVisible()) {
            frametag = new XMLElement("fieldset");
            legendtag = new XMLElement("legend");
            text = frame.getText();

            legendtag.add("class", frame.getLegendStyle());
            legendtag.addInner(text);
            
            frametag.add("id", frame.getName());
            frametag.add("class", frame.getStyleClass());
            frametag.addChild(legendtag);
        } else {
            frametag = new XMLElement("div");
        }
        frametag.addChildren(renderElements(frame.getElements(), config));
        
        return frametag;
    }
}
