package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

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
    public void onEvent(byte event, ControlComponent control) {
        int pages, topline;
        pages = context.data.objects.size() /
                (topline = context.data.vlength);
        context.data.topline = (topline *= pages);
        move();
    }

}
