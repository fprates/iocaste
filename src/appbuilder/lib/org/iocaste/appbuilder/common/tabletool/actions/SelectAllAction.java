package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class SelectAllAction extends TableToolAction {

    public SelectAllAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, "select_all");
        setMarkable(true);
        setText("pt_BR", "Marcar todos [☑]");
    }
    
    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.selectAll(true);
    }

}
