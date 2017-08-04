package org.iocaste.examples.dataformuse;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class DataFormUsePage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new DataFormUsePageSpec());
        set(new DataFormUsePageConfig());
        set(new DataFormUsePageInput());
        action("update", new DataFormUseUpdate());
    }

}
