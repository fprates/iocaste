package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class FirstAction extends TableToolAction {
    private static final long serialVersionUID = 5093350441578939032L;

    public FirstAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, "first");
        setText("<<");
        setNavigable(true);
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.first();
    }

}
