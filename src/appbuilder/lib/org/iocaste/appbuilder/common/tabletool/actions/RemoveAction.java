package org.iocaste.appbuilder.common.tabletool.actions;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class RemoveAction extends TableToolAction {

    public RemoveAction(TableTool tabletool, TableToolData data,
            Map<String, TableToolAction> store) {
        super(tabletool, data, store, "remove");
        setText("pt_BR", "Remover [-]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.remove();
    }

}
