package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class EntityPage extends AbstractPanelPage {
    public String action;
    public AbstractPanelSpec spec;
    public AppBuilderLink link;
    
    @Override
    public void execute() {
        set(spec);
        
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
    }
    
    private final void setSelectConfig(ViewConfig config, AppBuilderLink link) {
        if (config == null)
            set(new SelectConfig(link.cmodel));
        else
            set(config);
    }

}
