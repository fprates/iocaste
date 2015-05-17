package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;
import org.iocaste.appbuilder.common.panel.Colors;

public class EntityPage extends AbstractPanelPage {
    public String action;
    public AbstractPanelSpec spec;
    public AppBuilderLink link;
    
    @Override
    public void execute() {
        Context extcontext;
        
        set(spec);
        set(Colors.BODY_BG, "#ffffff");
        extcontext = getExtendedContext();
        
        switch (action) {
        case AbstractModelViewer.CREATE:
            setSelectConfig(link.createselectconfig, action, extcontext, link);
            action(action, link.validate);
            break;
        case AbstractModelViewer.EDIT:
            setSelectConfig(link.updateselectconfig, action, extcontext, link);
            action(action, link.updateload);
            break;
        case AbstractModelViewer.DISPLAY:
            setSelectConfig(link.displayselectconfig, action, extcontext, link);
            action(action, link.displayload);
            break;
        }
    }
    
    private final void setSelectConfig(ViewConfig config,
            String action, Context context, AppBuilderLink link) {
        if (config == null)
            set(new SelectConfig(action, link.cmodel));
        else
            set(config);
    }

}
