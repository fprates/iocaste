package org.iocaste.appbuilder.common.tabletool;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

public class AddItem extends AbstractTableHandler {

    public static final void run(TableTool tabletool, TableToolData data) {
        int i = 0;
        Context context = new Context();
        Table table = tabletool.getElement();
        
        context.data = data;
        switch (context.data.mode) {
        case TableTool.CONTINUOUS_UPDATE:
            for (TableItem item_ : table.getItems()) {
                if (!item_.isSelected()) {
                    i++;
                    continue;
                }
                break;
            }

            additem(tabletool, context, null, i);
            break;
        default:
            additems(tabletool, context, null);
            break;
        }
    }

}
