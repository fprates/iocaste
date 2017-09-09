package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.kernel.runtime.shell.elements.VirtualControl;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;

public class VirtualControlRenderer
        extends AbstractElementRenderer<VirtualControl> {

    public VirtualControlRenderer(HtmlRenderer renderer) {
        super(renderer, Const.VIRTUAL_CONTROL);
    }

    @Override
    protected XMLElement execute(VirtualControl control, Config config) {
        config.viewctx.getEventHandler(control.getName(),
                control.getAction(), "").name = control.getHtmlName();
        return null;
    }

}
