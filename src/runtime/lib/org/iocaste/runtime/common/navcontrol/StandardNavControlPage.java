package org.iocaste.runtime.common.navcontrol;

import org.iocaste.runtime.common.page.AbstractPage;

public class StandardNavControlPage extends AbstractPage {

    @Override
    public void execute() throws Exception {
        set(new StandardNavControlSpec());
        set(new StandardNavControlConfig());
        set(new StandardNavControlInput());
    }

}
