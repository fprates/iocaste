package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.dataformtool.DataFormSpecItemHandler;
import org.iocaste.kernel.runtime.shell.dataformtool.DataFormTool;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;

public class DataFormFactory extends AbstractSpecFactory {
    
    public DataFormFactory() {
        setHandler(new DataFormSpecItemHandler());
    }
    
    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        String name;
        DataForm dataform = viewctx.view.getElement(htmlname);
        for (Element element : dataform.getElements())
            if (element.isVisible())
                viewctx.addEventHandler(
                        name = element.getHtmlName(), name, "focus");
    }
    
    @Override
    public final void generate(
    		ViewContext viewctx, ComponentEntry entry, String prefix) {
        entry.component = new DataFormTool(viewctx, entry);
    }
}
