package org.iocaste.documents;

import org.iocaste.documents.range.NumericRangeFunction;
import org.iocaste.protocol.ServerServlet;

public class Servlet extends ServerServlet {
    private static final long serialVersionUID = -3216438189915047776L;

    @Override
    protected void config() {
        register(new NumericRangeFunction());
    }

}
