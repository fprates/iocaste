package org.iocaste.shell.common;

import org.iocaste.protocol.AbstractFunction;

public class AbstractView extends AbstractFunction {

    public AbstractView() {
        export("get_view_data", "getViewData");
    }
}
