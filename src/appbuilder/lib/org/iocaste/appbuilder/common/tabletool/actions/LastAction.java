package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class LastAction extends TableToolAction {

    public LastAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, "last");
        setNavigable(true);
        setText("pt_BR", "Último [>>]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.last();
    }

}
