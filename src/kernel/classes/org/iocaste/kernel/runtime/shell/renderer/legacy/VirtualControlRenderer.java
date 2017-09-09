package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.VirtualControl;

public class VirtualControlRenderer
        extends AbstractElementRenderer<VirtualControl> {

    public VirtualControlRenderer(HtmlRenderer renderers) {
        super(renderers, Const.VIRTUAL_CONTROL);
    }

    @Override
    protected XMLElement execute(VirtualControl control, Config config) {
        config.viewctx.getEventHandler(control.getName(),
                control.getAction(), "").name = control.getHtmlName();
        return null;
    }

}
