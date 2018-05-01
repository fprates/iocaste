package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableContextItem;

public class AddAction extends AbstractTableToolAction {
    private static final long serialVersionUID = -4893599416284335829L;

    public AddAction(TableContext context, Map<String, AbstractTableToolAction> store) {
        super(context, store, "add");
        setText("pt_BR", "Adicionar [+]");
    }

    @Override
    public void onEvent(ControlComponent control) {
        Map<String, TableContextItem> ctxitems;
        Table table = context.tabletool.getElement();
        
        if (table.getItems().size() > 0) {
            ctxitems = table.getContextItems();
            ctxitems.get("accept").visible = true;
            ctxitems.get("add").visible = false;
            ctxitems.get("remove").visible = false;
        }
//        
//        additems();
//        installValidators();
    }

}
