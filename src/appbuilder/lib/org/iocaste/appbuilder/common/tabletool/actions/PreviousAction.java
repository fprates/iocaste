package org.iocaste.appbuilder.common.tabletool.actions;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class PreviousAction extends TableToolAction {

    public PreviousAction(TableTool tabletool, TableToolData data,
            Map<String, TableToolAction> store) {
        super(tabletool, data, store, "previous");
        setNavigable(true);
        setText("pt_BR", "Anterior [<]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.previous();
    }

}
