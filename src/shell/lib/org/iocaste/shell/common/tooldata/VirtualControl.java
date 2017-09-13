package org.iocaste.shell.common.tooldata;

import org.iocaste.shell.common.Const;

public class VirtualControl extends ToolDataElement {
    private static final long serialVersionUID = -5066437754126116647L;
    
    public VirtualControl(Context viewctx, String name) {
        super(viewctx, Const.VIRTUAL_CONTROL, name);
    }
    
    @Override
    public final boolean isControlComponent() {
        return true;
    }

}
