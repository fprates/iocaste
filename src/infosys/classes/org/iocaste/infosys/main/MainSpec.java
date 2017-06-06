package org.iocaste.infosys.main;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewSpec;

public class MainSpec extends AbstractViewSpec {

    @Override
    protected void execute(Context context) {
        tabletool(parent, "connections");
    }

}
