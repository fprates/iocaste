package org.iocaste.kernel.runtime.shell.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Canvas;
import org.iocaste.shell.common.Const;

public class CanvasRenderer extends AbstractElementRenderer<Canvas> {
    
    public CanvasRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.CANVAS);
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
