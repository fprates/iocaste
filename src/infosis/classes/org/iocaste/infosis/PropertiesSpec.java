package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class PropertiesSpec extends AbstractViewSpec {

    @Override
    protected void execute() {
        form("main");
        navcontrol("main");
        reporttool("main", "properties");
    }

}
