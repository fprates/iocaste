package org.iocaste.kernel.runtime.shell.tabletool;

import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.tooldata.ToolData;

public class AddItem extends AbstractTableHandler {

    public static final void run(TableTool tabletool, ToolData data) {
        int i = 0;
        TableContext context = new TableContext();
        Table table = tabletool.getElement();
        
        context.data = data;
        context.tabletool = tabletool;
        if (context.data.vlength > 0) {
            for (TableItem item_ : table.getItems()) {
                if (!item_.isSelected()) {
                    i++;
                    continue;
                }
                break;
            }
            additem(context, null, i);
        } else {
            additems(context);
        }
    }

}
