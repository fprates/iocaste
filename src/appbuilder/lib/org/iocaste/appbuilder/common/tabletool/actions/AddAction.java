package org.iocaste.appbuilder.common.tabletool.actions;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class AddAction extends TableToolAction {

    public AddAction(TableTool tabletool, TableToolData data,
            Map<String, TableToolAction> store) {
        super(tabletool, data, store, "add");
        setText("pt_BR", "Adicionar [+]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.add();
    }

}
