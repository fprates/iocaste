package org.iocaste.exhandler;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
        set(new Style());
    }
    
}

