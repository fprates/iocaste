package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.PrintArea;

public class PrintAreaRenderer extends AbstractElementRenderer<PrintArea> {

    public PrintAreaRenderer(HtmlRenderer renderers) {
        super(renderers, Const.PRINT_AREA);
    }

    @Override
    protected final XMLElement execute(PrintArea printarea, Config config) {
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
