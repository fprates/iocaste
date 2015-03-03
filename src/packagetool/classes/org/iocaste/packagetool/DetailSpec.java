package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class DetailSpec extends AbstractViewSpec {

    @Override
    protected void execute() {
        form("main");
        navcontrol("main");
    }

}
