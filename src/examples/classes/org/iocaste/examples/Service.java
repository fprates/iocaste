package org.iocaste.examples;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Service extends AbstractFunction {

    public Service() {
        export("ping", "ping");
    }
    
    public final int ping(Message message) {
        return 1;
    }
}
