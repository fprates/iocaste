package org.iocaste.kernel.runtime.shell.renderer.listbox;

import org.iocaste.kernel.runtime.shell.renderer.AbstractSource;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.DataItem;

public class ListBoxDataItemSource extends AbstractSource {

    @Override
    public Object run() {
        XMLElement selecttag = (XMLElement)get("select");
        selecttag.add("style", "display:block");
        return ((DataItem)get("input")).getValues();
    }
    
}

