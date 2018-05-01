package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

public class DeselectAllAction extends AbstractTableToolAction {
    private static final long serialVersionUID = -1613073092365006156L;

    public DeselectAllAction(
            TableContext context, Map<String, AbstractTableToolAction> store) {
        super(context, store, "deselect_all");
        setMarkable(true);
        setText("pt_BR", "Desmarcar todos [☐]");
    }

    @Override
    public void onEvent(ControlComponent control) {
        Table table = context.tabletool.getElement();
        ComponentEntry entry = context.viewctx.entries.get(context.name);
        entry.component.load();
        for (TableItem item : table.getItems())
            item.setSelected(false);
        save(entry.data);
    }

}
