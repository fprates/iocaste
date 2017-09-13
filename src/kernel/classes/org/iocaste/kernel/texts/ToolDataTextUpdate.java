package org.iocaste.kernel.texts;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.tooldata.ToolData;

public class ToolDataTextUpdate extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String textname = message.get("textname");
        ToolData tooldata = message.get("tooldata");
        Texts texts = getFunction();
        String sessionid = message.getSessionid();
        
        for (String id : tooldata.values.keySet())
            texts.update(
                    sessionid, textname, id, (String)tooldata.values.get(id));
        return null;
    }
    
}
