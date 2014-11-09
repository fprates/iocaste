package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.protocol.Message;

public class SetObjects extends AbstractTableHandler {

    @Override
    public Object run(Message message) throws Exception {
        TableToolData data = message.get("data");
        
        setObject(data);
        return data;
    }

}
