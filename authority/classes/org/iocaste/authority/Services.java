package org.iocaste.authority;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("remove", "remove");
    }
    
    public final void remove(Message message) {
        
    }
}
