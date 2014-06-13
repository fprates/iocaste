package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class MainSpec extends AbstractViewSpec {

    @Override
    protected void execute() {
        form("main");
        navcontrol("main");
        dashboard("main", "menu");
        dashboarditem("menu", "items");
    }

}
