package org.iocaste.appbuilder.common.tabletool.actions;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class SelectAllAction extends TableToolAction {

    public SelectAllAction(TableTool tabletool, TableToolData data,
            Map<String, TableToolAction> store) {
        super(tabletool, data, store, "select_all");
        setMarkable(true);
        setText("pt_BR", "Marcar todos [☑]");
    }
    
    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.selectAll(true);
    }

}
