package org.iocaste.copy;

import org.iocaste.protocol.AbstractFunction;

public class Service extends AbstractFunction {

    public Service() {
        export("send", new Send());
    }
}
