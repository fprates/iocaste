package org.iocaste.appbuilder;

import java.util.List;

import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.View;

public class SetMultipleObjects extends AbstractTableHandler {

    @Override
    public Object run(Message message) throws Exception {
        Object[] objects;
        List<TableToolData> tables = message.get("tables");
        View view = message.get("view");
        Context context = new Context();
        
        for (TableToolData data : tables) {
            context.data = data;
            context.data.getContainer().setView(view);
            context.data.last = 0;
            context.table = getTable(context.data);
            context.table.clear();
            setObjects(context);
            context.data.getContainer().setView(null);
        }
        
        objects = new Object[2];
        objects[0] = view;
        objects[1] = tables;
        return objects;
    }

}
