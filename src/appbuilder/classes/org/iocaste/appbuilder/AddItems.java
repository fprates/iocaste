package org.iocaste.appbuilder;

import org.iocaste.protocol.Message;

public class AddItems extends AbstractTableHandler {

    @Override
    public Object run(Message message) throws Exception {
        Context context = new Context();
        
        context.data = message.get("data");
        context.table = getTable(context.data);
        additems(context, context.data.objects);
        
        return context.data;
    }

}
