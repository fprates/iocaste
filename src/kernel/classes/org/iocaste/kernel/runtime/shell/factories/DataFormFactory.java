package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.dataformtool.DataFormSpecItemHandler;
import org.iocaste.kernel.runtime.shell.dataformtool.DataFormTool;

public class DataFormFactory extends AbstractSpecFactory {
    
    public DataFormFactory() {
        setHandler(new DataFormSpecItemHandler());
    }
    
    @Override
    public final void generate(
    		ViewContext viewctx, ComponentEntry entry, String prefix) {
        entry.component = new DataFormTool(viewctx, entry);
    }
}
