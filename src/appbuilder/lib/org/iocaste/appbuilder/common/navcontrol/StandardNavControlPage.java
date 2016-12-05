package org.iocaste.appbuilder.common.navcontrol;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class StandardNavControlPage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new StandardNavControlSpec());
        set(new StandardNavControlConfig());
    }

}
