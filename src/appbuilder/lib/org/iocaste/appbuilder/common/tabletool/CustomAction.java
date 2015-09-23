package org.iocaste.appbuilder.common.tabletool;

import org.iocaste.appbuilder.common.tabletool.actions.TableToolAction;
import org.iocaste.shell.common.AbstractContext;

public class CustomAction extends TableToolAction {
    private static final long serialVersionUID = -377995770128702096L;

    public CustomAction(TableTool tabletool, TableToolData data, String action) {
        super(tabletool, data, action, false);
    }

    @Override
    public final void execute(AbstractContext context) throws Exception { }

}
