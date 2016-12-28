package org.iocaste.sysconfig;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class MainPanel extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        ExtendedContext extcontext;
        
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
        action("save", new Save());
        put("load", new Load());
        
        extcontext = getExtendedContext();
        extcontext.pageInstance("main");
        extcontext.getContext().run("main", "load");
    }
    
}
