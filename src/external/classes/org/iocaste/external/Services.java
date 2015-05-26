package org.iocaste.external;

import org.iocaste.protocol.AbstractFunction;

public class Services extends AbstractFunction {
    public int counter;
    
    public Services() {
        export("connect", new Connect());
    }
}
