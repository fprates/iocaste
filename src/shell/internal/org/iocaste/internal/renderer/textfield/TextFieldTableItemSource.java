package org.iocaste.internal.renderer.textfield;

import org.iocaste.internal.renderer.AbstractSource;

public class TextFieldTableItemSource extends AbstractSource {

    @Override
    public Object run() {
        ((StringBuilder)get("sb")).append("table_cell_content");
        return "text_field_cell";
    }
    
}

