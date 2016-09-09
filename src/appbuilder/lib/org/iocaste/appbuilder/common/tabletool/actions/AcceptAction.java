package org.iocaste.appbuilder.common.tabletool.actions;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class AcceptAction extends TableToolAction {

    public AcceptAction(TableTool tabletool, TableToolData data,
            Map<String, TableToolAction> store) {
        super(tabletool, data, store, "accept");
        setText("pt_BR", "Aceitar");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.accept();
    }

}
