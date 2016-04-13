package org.iocaste.tasksel;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class Refresh extends AbstractHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        Main function = getFunction();
        
        function.refresh();
        
        return null;
    }

}
