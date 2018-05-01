package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.ControlComponent;

public class FirstAction extends AbstractTableToolAction {
    private static final long serialVersionUID = -990742573782663799L;

    public FirstAction(
            TableContext context, Map<String, AbstractTableToolAction> store) {
        super(context, store, "first");
        setNavigable(true);
        setText("pt_BR", "Primeiro [<<]");
    }

    @Override
    public void onEvent(ControlComponent control) {
        context.viewctx.entries.get(context.name).data.topline = 0;
        move();
    }

}
