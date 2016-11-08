package org.iocaste.dataview.ns;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class NSInputPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new NSInputSpec());
        set(new NSInputConfig());
        submit("continuesel", new ContinueSelect());
    }
    
}