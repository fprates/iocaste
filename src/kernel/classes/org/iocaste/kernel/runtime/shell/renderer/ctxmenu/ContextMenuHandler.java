package org.iocaste.kernel.runtime.shell.renderer.ctxmenu;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.AbstractEventHandler;
import org.iocaste.shell.common.ControlComponent;

public class ContextMenuHandler extends AbstractEventHandler {
    private static final long serialVersionUID = 6213561752776374524L;
    private ViewContext viewctx;
    
    public ContextMenuHandler(ViewContext viewctx) {
        this.viewctx = viewctx;
    }
    
    @Override
    public void onEvent(byte event, ControlComponent control) {
        if ((control == null) || !control.isPopup())
            return;
        viewctx.viewexport.popupcontrol = control.getHtmlName();
    }
    
}
