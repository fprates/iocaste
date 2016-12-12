package org.iocaste.install.finish;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.install.Style;

public class FinishPage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new FinishSpec());
        set(new FinishConfig());
        set(new Style());
    }
    
}
