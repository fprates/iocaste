package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class SelectSpec extends AbstractViewSpec {

    @Override
    protected void execute() {
        form("main");
        navcontrol("main");
        dataform("main", "model");
    }
}
