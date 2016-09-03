package org.iocaste.internal.renderer.textfield;

import org.iocaste.internal.renderer.AbstractSource;
import org.iocaste.shell.common.InputComponent;

public class TextFieldSource extends AbstractSource {

    @Override
    public Object run() {
        return ((InputComponent)get("input")).getName();
    }
    
}
