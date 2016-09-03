package org.iocaste.internal.renderer.textfield;

import org.iocaste.internal.renderer.AbstractSource;

public class TextFieldContainerSource extends AbstractSource {

    @Override
    public Object run() {
        ((StringBuilder)get("sb")).append((String)get("style"));
        return "text_field_regular";
    }
    
}