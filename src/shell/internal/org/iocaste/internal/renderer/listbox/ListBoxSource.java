package org.iocaste.internal.renderer.listbox;

import org.iocaste.internal.renderer.AbstractSource;
import org.iocaste.shell.common.ListBox;

public class ListBoxSource extends AbstractSource {

    @Override
    public Object run() {
        return ((ListBox)get("input")).properties();
    }
    
}

