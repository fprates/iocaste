package org.iocaste.kernel.runtime.shell.renderer.listbox;

import org.iocaste.kernel.runtime.shell.renderer.AbstractSource;
import org.iocaste.shell.common.ListBox;

public class ListBoxSource extends AbstractSource {

    @Override
    public Object run() {
        return ((ListBox)get("input")).properties();
    }
    
}

