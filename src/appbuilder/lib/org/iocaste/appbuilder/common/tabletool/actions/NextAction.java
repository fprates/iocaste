package org.iocaste.appbuilder.common.tabletool.actions;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class NextAction extends TableToolAction {

    public NextAction(TableTool tabletool, TableToolData data,
            Map<String, TableToolAction> store) {
        super(tabletool, data, store, "next");
        setNavigable(true);
        setText("pt_BR", "Próximo [>]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.next();
    }

}
