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
        switch (context.data.mode) {
        case TableTool.CONTINUOUS_UPDATE:
            for (TableItem item_ : table.getItems()) {
                if (!item_.isSelected()) {
                    i++;
                    continue;
                }
                break;
            }

            additem(context, null, i);
            break;
        default:
            additems(context);
            break;
        }
    }

}
