package org.iocaste.transport;

import org.iocaste.protocol.AbstractFunction;

public class Services extends AbstractFunction {

    public Services() {
        export("cancel", new CancelTransport());
        export("finish", new FinishTransport());
        export("send", new SendTransport());
        export("start", new StartTransport());
    }
}
