package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Frame;

public class FrameRenderer extends Renderer {
    
    /**
     * 
     * @param frame
     * @param config
     * @return
     */
    public static final XMLElement render(Frame frame, Config config) {
        XMLElement frametag = new XMLElement("fieldset");
        XMLElement legendtag = new XMLElement("legend");
        String text = frame.getText();
        String styleclass = frame.getStyleClass();

        legendtag.add("class", styleclass);
        legendtag.addInner(text);
        
        frametag.add("id", frame.getName());
        frametag.add("class", styleclass);
        frametag.addChild(legendtag);
        frametag.addChildren(renderElements(frame.getElements(), config));
        
        return frametag;
    }
}
