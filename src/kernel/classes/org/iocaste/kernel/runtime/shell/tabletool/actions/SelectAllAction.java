package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

public class SelectAllAction extends AbstractTableToolAction {
    private static final long serialVersionUID = -4791446965302700872L;

    public SelectAllAction(
            TableContext context, Map<String, AbstractTableToolAction> store) {
        super(context, store, "select_all");
        setMarkable(true);
        setText("pt_BR", "Marcar todos [☑]");
    }

    @Override
    public void onEvent(ControlComponent control) {
        Table table = context.tabletool.getElement();
        ComponentEntry entry = context.viewctx.entries.get(context.name);
        
        entry.component.load();
        for (TableItem item : table.getItems())
            item.setSelected(true);
        save(entry.data);
    }

}
