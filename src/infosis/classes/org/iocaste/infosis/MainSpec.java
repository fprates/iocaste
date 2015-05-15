package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        form("main");
        navcontrol("main");
        dashboard("main", "menu");
        for (String name : Main.ACTIONS)
            dashboarditem("menu", name);
    }

}
