package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.ControlComponent;

public class PreviousAction extends AbstractTableToolAction {
    private static final long serialVersionUID = -2332387527308528041L;

    public PreviousAction(
            TableContext context, Map<String, AbstractTableToolAction> store) {
        super(context, store, "previous");
        setNavigable(true);
        setText("pt_BR", "Anterior [<]");
    }

    @Override
    public void onEvent(ControlComponent control) {
        int topline = context.data.topline - context.data.vlength;
        if (topline < 0)
            return;
        context.data.topline = topline;
        move();
    }

}
