package org.iocaste.external.common;

import org.iocaste.protocol.AbstractFunction;

public class IocasteConnector extends AbstractFunction {
    
    public IocasteConnector(String address) {
        setServerName(address);
    }
}