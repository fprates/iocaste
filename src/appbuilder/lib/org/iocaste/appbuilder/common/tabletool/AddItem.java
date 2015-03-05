package org.iocaste.appbuilder.common.tabletool;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.shell.common.TableItem;

public class AddItem extends AbstractTableHandler {

    public static final void run(TableToolData data) {
        int i = 0;
        Context context = new Context();
        
        context.data = data;
        context.table = getTable(context.data);
        
        switch (context.data.mode) {
        case TableTool.CONTINUOUS_UPDATE:
            for (TableItem item_ : context.table.getItems()) {
                if (!item_.isSelected()) {
                    i++;
                    continue;
                }
                break;
            }

            context.table.setVisibleLines(context.data.vlines);
            additem(context, null, i);
            break;
        default:
            additems(context, null);
            break;
        }
    }

}
