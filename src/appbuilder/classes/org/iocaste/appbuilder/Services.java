package org.iocaste.appbuilder;

import org.iocaste.protocol.AbstractFunction;

public class Services extends AbstractFunction {
    
    public Services() {
        export("nc_data_get", new GetStyleSheet());
    }
}
