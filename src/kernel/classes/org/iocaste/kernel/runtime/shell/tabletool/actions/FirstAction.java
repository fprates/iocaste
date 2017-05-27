package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.AbstractContext;

public class FirstAction extends TableToolAction {

    public FirstAction(
            TableContext context, Map<String, TableToolAction> store) {
        super(context, store, "first");
        setNavigable(true);
        setText("pt_BR", "Primeiro [<<]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        this.context.tabletool.first();
    }

}
