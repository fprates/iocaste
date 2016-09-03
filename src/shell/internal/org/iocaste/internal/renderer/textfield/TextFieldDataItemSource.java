package org.iocaste.internal.renderer.textfield;

import org.iocaste.internal.renderer.AbstractSource;
import org.iocaste.shell.common.DataItem;

public class TextFieldDataItemSource extends AbstractSource {

    @Override
    public Object run() {
        return ((DataItem)get("input")).getLabel();
    }
    
}

