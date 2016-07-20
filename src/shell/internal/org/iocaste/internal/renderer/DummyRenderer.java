package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;

public class DummyRenderer extends AbstractElementRenderer<Element> {

    public DummyRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.DUMMY);
    }

    @Override
    protected XMLElement execute(Element element, Config config) {
        return null;
    }

}
