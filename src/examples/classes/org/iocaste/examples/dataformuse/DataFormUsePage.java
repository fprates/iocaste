package org.iocaste.examples.dataformuse;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.portal.PortalStyle;

public class DataFormUsePage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new DataFormUsePageSpec());
        set(new DataFormUsePageConfig());
        set(new DataFormUsePageInput());
        set(new PortalStyle());
        action("update", new DataFormUseUpdate());
    }

}
