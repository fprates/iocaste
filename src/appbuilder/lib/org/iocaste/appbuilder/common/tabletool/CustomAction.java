package org.iocaste.appbuilder.common.tabletool;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.actions.TableToolAction;
import org.iocaste.shell.common.AbstractContext;

public class CustomAction extends TableToolAction {

    public CustomAction(TableTool tabletool, TableToolData data,
            Map<String, TableToolAction> store, String action) {
        super(tabletool, data, store, action, false);
    }

    @Override
    public final void execute(AbstractContext context) throws Exception { }

}
