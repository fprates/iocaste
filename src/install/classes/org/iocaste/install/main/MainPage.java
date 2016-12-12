package org.iocaste.install.main;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.install.Style;

public class MainPage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new MainSpec());
        set(new MainConfig());
        set(new Style());
        put("continue", new SettingsContinue());
    }
    
}
