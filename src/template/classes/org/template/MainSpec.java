package org.template;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class MainSpec extends AbstractViewSpec {

    @Override
    protected void execute() {
        form("main");
        navcontrol("main");
        text("main", "info");
        button("main", "bind");
    }

}
