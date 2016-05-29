package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.PrintArea;

public class PrintAreaRenderer extends Renderer {

    public static final XMLElement render(PrintArea printarea) {
        XMLElement pre;
        String value;
        
        pre = new XMLElement("pre");
        pre.add("style", "float:left; width:100%");
        value = printarea.getst();
        if (value != null)
            pre.addInner(value);
        
        return pre;
    }
}
