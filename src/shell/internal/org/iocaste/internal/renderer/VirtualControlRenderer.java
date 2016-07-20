package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.VirtualControl;

public class VirtualControlRenderer
        extends AbstractElementRenderer<VirtualControl> {

    public VirtualControlRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.VIRTUAL_CONTROL);
    }

    @Override
    protected XMLElement execute(VirtualControl control, Config config) {
        config.addAction(control.getAction(), control.getHtmlName());
        return null;
    }

}
