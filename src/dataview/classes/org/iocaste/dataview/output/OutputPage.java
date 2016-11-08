package org.iocaste.dataview.output;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class OutputPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new OutputSpec());
        set(new OutputConfig());
        set(new OutputInput());
    }
    
}
