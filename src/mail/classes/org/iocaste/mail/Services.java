package org.iocaste.mail;

import org.iocaste.protocol.AbstractFunction;

public class Services extends AbstractFunction {

    public Services() {
        export("send", new Send());
    }
}
