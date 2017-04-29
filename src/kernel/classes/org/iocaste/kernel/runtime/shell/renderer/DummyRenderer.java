package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;

public class DummyRenderer extends AbstractElementRenderer<Element> {

    public DummyRenderer(HtmlRenderer renderer) {
        super(renderer, Const.DUMMY);
    }

    public DummyRenderer(HtmlRenderer renderer, Const type) {
        super(renderer, type);
    }
    
    @Override
    protected XMLElement execute(Element element, Config config) {
        return null;
    }

}
