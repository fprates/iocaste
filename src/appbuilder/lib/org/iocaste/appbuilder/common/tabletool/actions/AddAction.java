package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class AddAction extends TableToolAction {

    public AddAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, "add");
        setText("pt_BR", "Adicionar [+]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.add();
    }

}
