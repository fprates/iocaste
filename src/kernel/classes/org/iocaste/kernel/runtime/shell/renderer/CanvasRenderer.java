package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Canvas;
import org.iocaste.shell.common.Const;

public class CanvasRenderer extends AbstractElementRenderer<Canvas> {
    
    public CanvasRenderer(HtmlRenderer renderer) {
        super(renderer, Const.CANVAS);
    }

    @Override
    protected final XMLElement execute(Canvas canvas, Config config) {
        String htmlname = canvas.getHtmlName();
        XMLElement tag = new XMLElement("div");
        tag.add("name", htmlname);
        tag.add("id", htmlname);
        tag.addInner(canvas.get());
        return tag;
    }
}
