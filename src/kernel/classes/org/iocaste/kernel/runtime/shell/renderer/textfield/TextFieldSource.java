package org.iocaste.kernel.runtime.shell.renderer.textfield;

import org.iocaste.kernel.runtime.shell.renderer.AbstractSource;
import org.iocaste.shell.common.InputComponent;

public class TextFieldSource extends AbstractSource {

    @Override
    public Object run() {
        return ((InputComponent)get("input")).getLabel();
    }
    
}
