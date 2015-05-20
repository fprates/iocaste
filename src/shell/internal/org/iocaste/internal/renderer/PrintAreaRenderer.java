package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.PrintArea;

public class PrintAreaRenderer extends Renderer {

    public static final XMLElement render(PrintArea printarea) {
        XMLElement pre;
        
        pre = new XMLElement("pre");
        pre.addInner(printarea.getst());
        
        return pre;
    }
}
