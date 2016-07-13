package org.iocaste.appbuilder;

import org.iocaste.protocol.AbstractIocasteServlet;

public class Servlet extends AbstractIocasteServlet {
    private static final long serialVersionUID = 4151177567085199930L;

    @Override
    protected void config() {
        register(new Services());
        authorize("nc_data_get", null);
    }

}
