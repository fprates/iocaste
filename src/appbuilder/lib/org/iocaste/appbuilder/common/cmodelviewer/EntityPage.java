package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class EntityPage extends AbstractPanelPage {
    public String action;
    public ViewSpec spec;
    public AppBuilderLink link;
    
    @Override
    public void execute() {
        set(spec);
        set(new SelectInput());
        
        switch (action) {
        case AbstractModelViewer.CREATE:
            setSelectConfig(link.createselectconfig, link);
            action(action, link.validate);
            break;
        case AbstractModelViewer.EDIT:
            setSelectConfig(link.updateselectconfig, link);
            action(action, link.updateload);
            break;
        case AbstractModelViewer.DISPLAY:
            setSelectConfig(link.displayselectconfig, link);
            action(action, link.displayload);
            break;
        }
        submit("validate", link.inputvalidate);
    }
    
    private final void setSelectConfig(ViewConfig config, AppBuilderLink link) {
        if (config == null)
            set(new SelectConfig(link.cmodel));
        else
            set(config);
    }
}
