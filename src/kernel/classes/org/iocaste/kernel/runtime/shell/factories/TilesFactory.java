package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.tilestool.TilesTool;

public class TilesFactory extends AbstractSpecFactory {
    
    @Override
    public final void generate(
    		ViewContext viewctx, ComponentEntry entry, String prefix) {
        if (prefix == null)
            entry.component = new TilesTool(viewctx, entry);
    }
}
