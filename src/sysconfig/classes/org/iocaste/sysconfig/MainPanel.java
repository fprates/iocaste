package org.iocaste.sysconfig;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class MainPanel extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
        action("save", new Save());
        put("load", new Load());
        getExtendedContext().getContext().run("main", "load");
    }
    
}
