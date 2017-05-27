package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.AbstractContext;

public class RemoveAction extends TableToolAction {

    public RemoveAction(
            TableContext context, Map<String, TableToolAction> store) {
        super(context, store, "remove");
        setText("pt_BR", "Remover [-]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        this.context.tabletool.remove();
    }

}
