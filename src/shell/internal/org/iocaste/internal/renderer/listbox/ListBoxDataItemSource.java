package org.iocaste.internal.renderer.listbox;

import org.iocaste.internal.renderer.AbstractSource;
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

