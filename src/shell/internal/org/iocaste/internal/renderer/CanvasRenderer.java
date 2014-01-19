package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Canvas;

public class CanvasRenderer extends Renderer {
    public static final XMLElement render(Canvas canvas, Config config) {
        String htmlname = canvas.getHtmlName();
        XMLElement tag = new XMLElement("div");
        tag.add("name", htmlname);
        tag.add("id", htmlname);
        tag.addInner(canvas.get());
        return tag;
    }
}
