package org.iocaste.appbuilder.common.tabletool.actions;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class DeselectAllAction extends TableToolAction {

    public DeselectAllAction(TableTool tabletool, TableToolData data,
            Map<String, TableToolAction> store) {
        super(tabletool, data, store, "deselect_all");
        setMarkable(true);
        setText("pt_BR", "Desmarcar todos [☐]");
    }
    
    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.selectAll(false);
    }

}
