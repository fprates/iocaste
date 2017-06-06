package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.AbstractContext;

public class NextAction extends TableToolAction {

    public NextAction(TableContext context, Map<String, TableToolAction> store)
    {
        super(context, store, "next");
        setNavigable(true);
        setText("pt_BR", "Próximo [>]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        this.context.tabletool.next();
    }

}
