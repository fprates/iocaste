package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.PageBuilderContext;
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

class SelectInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        Map<String, ComponentEntry> entries;
        
        entries = context.getView().getComponents().entries;
        for (String key : entries.keySet())
            switch (entries.get(key).data.type) {
            case DATA_FORM:
                dfset(key, extcontext.dfobjectget(key));
                break;
            default:
                break;
            }
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
    
}