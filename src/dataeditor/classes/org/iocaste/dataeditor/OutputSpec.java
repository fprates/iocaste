package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class OutputSpec extends AbstractViewSpec {

    @Override
    protected void execute() {
        form("main");
        navcontrol("main");
        tabletool("main", "items");
    }

}
