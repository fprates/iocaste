package org.iocaste.dataview.main;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        submit("select", new Select());
    }
    
}
