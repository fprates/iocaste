package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class AddAction extends TableToolAction {
    private static final long serialVersionUID = 4038439233797280809L;

    public AddAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, TableTool.ADD);
        setText("+");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.add();
    }

}
