package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

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
        int topline = context.data.topline + context.data.vlength;
        if (topline > context.data.objects.size())
            return;
        context.data.topline = topline;
        move();
    }

}
