package org.iocaste.shell.common.handlers;

import org.iocaste.shell.common.AbstractEventHandler;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.tooldata.ViewExport;

public class ContextMenuHandler extends AbstractEventHandler {
    private static final long serialVersionUID = 6213561752776374524L;
    private ViewExport viewexport;
    
    public ContextMenuHandler(ViewExport viewexport) {
        this.viewexport = viewexport;
    }
    
    @Override
    public void onEvent(ControlComponent control) {
        if ((control == null) || !control.isPopup())
            return;
        viewexport.popupcontrol = control.getHtmlName();
    }
    
}
