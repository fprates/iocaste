package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class AcceptAction extends TableToolAction {
    private static final long serialVersionUID = 1596429561800912771L;

    public AcceptAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, "accept");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.accept();
    }

}
