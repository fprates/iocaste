package org.iocaste.examples.tabsuse;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.examples.tabletooluse.TableToolUseStart;

public class TabsUsePage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new TabsUseSpec());
        set(new TabsUseConfig());
        set(new StandardViewInput());
        put("start", new TableToolUseStart("tabs-use"));
        
        run("start");
    }
    
}

