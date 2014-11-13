package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class SelectionSpec extends AbstractViewSpec {

    @Override
    protected void execute() {
        form("main");
        navcontrol("main");
        dataform("main", "model");
    }

}
