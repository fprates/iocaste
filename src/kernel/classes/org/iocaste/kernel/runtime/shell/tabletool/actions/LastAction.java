package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.AbstractContext;

public class LastAction extends TableToolAction {

    public LastAction(TableContext context, Map<String, TableToolAction> store)
    {
        super(context, store, "last");
        setNavigable(true);
        setText("pt_BR", "Último [>>]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        this.context.tabletool.last();
    }

}
