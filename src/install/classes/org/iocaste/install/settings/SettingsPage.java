package org.iocaste.install.settings;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.install.Style;

public class SettingsPage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new SettingsSpec());
        set(new SettingsConfig());
        set(new SettingsInput());
        set(new Style());
        put("continue", new InstallContinue());
    }
    
}
