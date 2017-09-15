package org.iocaste.shell;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.Tools;
import org.iocaste.shell.internal.DefaultStyle;

public class GetStyleConstants extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        return Tools.toArray(DefaultStyle.instance(null).getConstants());
    }
    
}
