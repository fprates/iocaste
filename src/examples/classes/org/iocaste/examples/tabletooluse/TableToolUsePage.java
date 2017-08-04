package org.iocaste.examples.tabletooluse;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class TableToolUsePage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new TableToolUseSpec());
        set(new TableToolUseConfig());
        set(new TableToolUseInput());
        action("update", new TableToolUsePrint());
        put("start", new TableToolUseStart("tabletool-use"));
        
        run("start");
    }

}
