package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.AbstractContext;

public class PreviousAction extends TableToolAction {

    public PreviousAction(
            TableContext context, Map<String, TableToolAction> store) {
        super(context, store, "previous");
        setNavigable(true);
        setText("pt_BR", "Anterior [<]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        this.context.tabletool.previous();
    }

}
