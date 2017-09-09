package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Canvas;
import org.iocaste.shell.common.Const;

public class CanvasRenderer extends AbstractElementRenderer<Canvas> {
    
    public CanvasRenderer(HtmlRenderer renderers) {
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
