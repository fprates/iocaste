package org.iocaste.copy.main;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        set(new StandardViewInput());
        submit("copy", new Copy());
        put("portsource", new CopyFromPortSource());
        put("dbsource", new CopyFromDBSource());
    }
    
}
