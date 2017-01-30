package org.iocaste.examples.dataformuse;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class DataFormUsePageConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        AbstractComponentData tool;
        
        getNavControl().setTitle(context.view.getPageName());
        getElement("view_port").setStyleClass("portal_viewport");
        
        tool = getTool("input");
        tool.model = "EXAMPLES_DFUSE_INPUT";
        for (String key : new String[] {"NAME", "GIFT", "ADJECTIVE"})
            tool.instance(key).required = true;
    }
    
}
