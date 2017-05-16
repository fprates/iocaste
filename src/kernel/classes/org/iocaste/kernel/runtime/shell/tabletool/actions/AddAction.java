package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.AbstractContext;

public class AddAction extends TableToolAction {

    public AddAction(TableContext context, Map<String, TableToolAction> store) {
        super(context, store, "add");
        setText("pt_BR", "Adicionar [+]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        this.context.tabletool.add();
    }

}
