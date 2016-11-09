package org.iocaste.upload.main;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        action("upload", new Upload());
    }
    
}
