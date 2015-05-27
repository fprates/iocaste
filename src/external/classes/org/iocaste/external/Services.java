package org.iocaste.external;

import org.iocaste.protocol.AbstractFunction;

public class Services extends AbstractFunction {
    public int counter;
    
    public Services() {
        export("test", new Test());
        export("connect", new Connect());
        export("connection_data_get", new GetConnectionData());
    }
}
