package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;

public class DeselectAllAction extends TableToolAction {
    private static final long serialVersionUID = 643655153669188798L;

    public DeselectAllAction(TableTool tabletool, TableToolData data) {
        super(tabletool, data, "deselect_all");
        setText("☐");
    }
    
    @Override
    public void execute(AbstractContext context) throws Exception {
        tabletool.selectAll(false);
    }

}
