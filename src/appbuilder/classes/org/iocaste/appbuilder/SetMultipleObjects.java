package org.iocaste.appbuilder;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.protocol.Message;

public class SetMultipleObjects extends AbstractTableHandler {

    @Override
    public Object run(Message message) throws Exception {
        Map<String, TableToolData> tables = message.get("tables");
        Context context = new Context();
        
        for (String name : tables.keySet()) {
            context.data = tables.get(name);
            context.data.last = 0;
            context.table = getTable(context.data);
            context.table.clear();
            setObjects(context);
        }
        
        return tables;
    }

}
