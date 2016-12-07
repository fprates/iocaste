package org.iocaste.appbuilder.common.navcontrol;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.appbuilder.common.ViewSpecItem.TYPES;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class StandardNavControlSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        ViewSpecItem.TYPES type;
        String parent, name;
        AbstractPanelPage panel;
        
        for (int i = 0; i < context.ncspec.length; i++) {
            type = (TYPES)context.ncspec[i][0];
            parent = (String)context.ncspec[i][1];
            name = (String)context.ncspec[i][2];
            component(type, parent, name);
        }
        
        panel = context.getView().getPanelPage();
        for (String key : panel.getActions())
            button("actionbar", key);
        
        name = panel.getSubmit();
        if (name != null)
            button("actionbar", name);
    }
}