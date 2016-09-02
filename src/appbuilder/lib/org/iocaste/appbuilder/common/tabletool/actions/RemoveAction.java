package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class RemoveAction extends TableToolAction {

    public RemoveAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, "remove");
        setText("pt_BR", "Remover [-]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.remove();
    }

}
