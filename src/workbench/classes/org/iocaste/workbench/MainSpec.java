package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class MainSpec extends AbstractViewSpec {

    @Override
    public void execute() {
        form("main");
        navcontrol("main");
        dataform("main", "head");
    }

}
