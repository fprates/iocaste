package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.tabletool.TableTool;

public class TableToolFactory extends AbstractSpecFactory {

    @Override
    public final void generate(
    		ViewContext viewctx, ComponentEntry entry, String prefix) {
        entry.component = new TableTool(viewctx, entry);
    }
}
