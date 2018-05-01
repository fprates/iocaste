package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.ControlComponent;

public class LastAction extends AbstractTableToolAction {
    private static final long serialVersionUID = 37782992749744228L;

    public LastAction(
            TableContext context, Map<String, AbstractTableToolAction> store) {
        super(context, store, "last");
        setNavigable(true);
        setText("pt_BR", "Último [>>]");
    }

    @Override
    public void onEvent(ControlComponent control) {
        int pages, topline;
        ComponentEntry entry = context.viewctx.entries.get(context.name);
        pages = entry.data.objects.size() /
                (topline = entry.data.vlength);
        entry.data.topline = (topline *= pages);
        move();
    }

}
