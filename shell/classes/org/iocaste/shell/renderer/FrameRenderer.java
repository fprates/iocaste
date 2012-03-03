package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
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
        
        legendtag.addInner(frame.getText());
        
        frametag.add("id", frame.getName());
        frametag.addChild(legendtag);
        frametag.addChildren(renderElements(frame.getElements(), config));
        
        return frametag;
    }
}
