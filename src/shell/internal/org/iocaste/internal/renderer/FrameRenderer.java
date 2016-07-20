package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Frame;

public class FrameRenderer extends AbstractElementRenderer<Frame> {
    
    public FrameRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.FRAME);
    }

    /**
     * 
     * @param frame
     * @param config
     * @return
     */
    protected final XMLElement execute(Frame frame, Config config) {
        XMLElement frametag = new XMLElement("fieldset");
        XMLElement legendtag = new XMLElement("legend");
        String text = frame.getText();

        legendtag.add("class", frame.getLegendStyle());
        legendtag.addInner(text);
        
        frametag.add("id", frame.getName());
        frametag.add("class", frame.getStyleClass());
        frametag.addChild(legendtag);
        frametag.addChildren(renderElements(frame.getElements(), config));
        
        return frametag;
    }
}
