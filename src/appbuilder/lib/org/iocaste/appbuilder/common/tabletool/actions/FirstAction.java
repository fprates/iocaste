package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class FirstAction extends TableToolAction {

    public FirstAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, "first");
        setNavigable(true);
        setText("pt_BR", "Primeiro [<<]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.first();
    }

}
