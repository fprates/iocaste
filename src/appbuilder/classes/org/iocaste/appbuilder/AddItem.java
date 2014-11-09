package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.TableItem;

public class AddItem extends AbstractTableHandler {

    @Override
    public Object run(Message message) throws Exception {
        int i = 0;
        Context context = new Context();
        
        context.data = message.get("data");
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
            
            additem(context, null, i);
            break;
        default:
            additems(context, null);
            break;
        }
        
        return context.data;
    }

}
