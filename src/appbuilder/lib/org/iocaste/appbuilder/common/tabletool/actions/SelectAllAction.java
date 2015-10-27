package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class SelectAllAction extends TableToolAction {
    private static final long serialVersionUID = -270765093845726912L;

    public SelectAllAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, "select_all");
        setText("☑");
    }
    
    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.selectAll(true);
    }

}
