package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.ControlComponent;

public class NextAction extends AbstractTableToolAction {
    private static final long serialVersionUID = -3785804066512094959L;

    public NextAction(
            TableContext context, Map<String, AbstractTableToolAction> store) {
        super(context, store, "next");
        setNavigable(true);
        setText("pt_BR", "Próximo [>]");
    }

    @Override
    public void onEvent(ControlComponent control) {
        ComponentEntry entry = context.viewctx.entries.get(context.name);
        int topline = entry.data.topline + entry.data.vlength;
        if (topline > entry.data.objects.size())
            return;
        entry.data.topline = topline;
        move();
    }

}
