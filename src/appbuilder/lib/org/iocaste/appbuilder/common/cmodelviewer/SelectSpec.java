package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class SelectSpec extends AbstractViewSpec {

    @Override
    public void execute() {
        form("main");
        navcontrol("main");
        dataform("main", "head");
    }

}