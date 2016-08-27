package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class AbstractEntityPage extends AbstractPanelPage {
    public String action;
    public ViewSpec spec;
    public AppBuilderLink link;
    private ViewConfig createselectconfig;
    private ViewConfig updateselectconfig;
    private ViewConfig displayselectconfig;
    
    public AbstractEntityPage(
            ViewConfig createselectconfig,
            ViewConfig updateselectconfig,
            ViewConfig displayselectconfig) {
        this.createselectconfig = createselectconfig;
        this.updateselectconfig = updateselectconfig;
        this.displayselectconfig = displayselectconfig;
    }
    
    @Override
    public void execute() {
        set(spec);
        set(new SelectInput());
        
        switch (action) {
        case AbstractModelViewer.CREATE:
            setSelectConfig(createselectconfig, link);
            action(action, link.validate);
            break;
        case AbstractModelViewer.EDIT:
            setSelectConfig(updateselectconfig, link);
            action(action, link.updateload);
            break;
        case AbstractModelViewer.DISPLAY:
            setSelectConfig(displayselectconfig, link);
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
