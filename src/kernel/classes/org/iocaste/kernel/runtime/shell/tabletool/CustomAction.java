package org.iocaste.kernel.runtime.shell.tabletool;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.actions.TableToolAction;
import org.iocaste.shell.common.AbstractContext;

public class CustomAction extends TableToolAction {

    public CustomAction(TableContext context,
            Map<String, TableToolAction> store, String action) {
        super(context, store, action, false);
    }

    @Override
    public final void execute(AbstractContext context) throws Exception { }

}
