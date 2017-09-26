package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

public class RemoveAction extends AbstractTableToolAction {
    private static final long serialVersionUID = -3936860666585548536L;

    public RemoveAction(
            TableContext context, Map<String, AbstractTableToolAction> store) {
        super(context, store, "remove");
        setText("pt_BR", "Remover [-]");
    }

    @Override
    public void onEvent(byte event, ControlComponent control) {
        Table table;
        int index;
        
        context.viewctx.entries.get(context.data.name).component.load();
        table = context.tabletool.getElement();
        index = context.data.topline;
        
        for (TableItem item : table.getItems()) {
            if (!item.isSelected()) {
                index++;
                continue;
            }
            table.remove(item);
            context.data.objects.remove(index++);
        }
    }

}
