package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewInput;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public abstract class AbstractEntityDisplayPage extends AbstractPanelPage {
    public AppBuilderLink link;
    private ViewSpec maintenancespec;
    private ViewConfig displayconfig;
    private ViewInput maintenanceinput;
    
    public AbstractEntityDisplayPage(
            ViewSpec spec, ViewConfig config, ViewInput input) {
        this.maintenancespec = spec;
        this.displayconfig = config;
        this.maintenanceinput = input;
    }
    
    @Override
    public void execute() {
        set(maintenancespec);
        set(displayconfig);
        set(maintenanceinput);
    }

}
