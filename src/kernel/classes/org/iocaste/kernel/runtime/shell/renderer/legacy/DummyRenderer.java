package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;

public class DummyRenderer extends AbstractElementRenderer<Element> {

    public DummyRenderer(HtmlRenderer renderers) {
        super(renderers, Const.DUMMY);
    }

    public DummyRenderer(HtmlRenderer renderers, Const type) {
        super(renderers, type);
    }
    
    @Override
    protected XMLElement execute(Element element, Config config) {
        return null;
    }

}
