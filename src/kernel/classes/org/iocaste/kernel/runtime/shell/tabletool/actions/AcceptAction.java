package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableContextItem;

public class AcceptAction extends AbstractTableToolAction {
    private static final long serialVersionUID = 6910686892818680814L;

    public AcceptAction(
            TableContext context, Map<String, AbstractTableToolAction> store) {
        super(context, store, "accept");
        setText("pt_BR", "Aceitar");
    }

    @Override
    public void onEvent(ControlComponent control) {
        Map<String, TableContextItem> ctxitems;
        
        ctxitems = ((Table)context.tabletool.getElement()).getContextItems();
        ctxitems.get("accept").visible = false;
        ctxitems.get("add").visible = true;
        ctxitems.get("remove").visible = true;
        context.data.topline = 0;
    }

}
