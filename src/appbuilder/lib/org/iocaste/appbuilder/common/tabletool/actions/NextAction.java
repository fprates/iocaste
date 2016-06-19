package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class NextAction extends TableToolAction {
    private static final long serialVersionUID = 8199192613799376990L;

    public NextAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, "next");
        setNavigable(true);
        setText("pt_BR", "Próximo [>]");
    }

    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.next();
    }

}
