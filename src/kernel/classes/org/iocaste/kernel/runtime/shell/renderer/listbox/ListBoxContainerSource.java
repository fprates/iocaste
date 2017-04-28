package org.iocaste.kernel.runtime.shell.renderer.listbox;

import org.iocaste.kernel.runtime.shell.renderer.AbstractSource;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;

public class ListBoxContainerSource extends AbstractSource {

    @Override
    public Object run() {
        String style = ((InputComponent)get("input")).getStyleClass();
        return (style == null)? Const.LIST_BOX.style() : style;
    }
    
}