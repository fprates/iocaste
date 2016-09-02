package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class PreviousAction extends TableToolAction {

    public PreviousAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, "previous");
        setNavigable(true);
        setText("pt_BR", "Anterior [<]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.previous();
    }

}
