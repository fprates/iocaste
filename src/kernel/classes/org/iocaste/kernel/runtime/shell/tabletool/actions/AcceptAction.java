package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.AbstractContext;

public class AcceptAction extends TableToolAction {

    public AcceptAction(
            TableContext context, Map<String, TableToolAction> store) {
        super(context, store, "accept");
        setText("pt_BR", "Aceitar");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        this.context.tabletool.accept();
    }

}
