package org.iocaste.examples.tabletooluse;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class TableToolUseConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TableToolData tool;
        
        getNavControl().setTitle(context.view.getPageName());
        getElement("viewport").setStyleClass("portal_viewport");
        
        tool = getTool("items");
        tool.model = "EXAMPLES_TTUSE_ITEM";
        tool.vlines = 10;
        tool.mark = false;
        tool.mode = TableTool.CONTINUOUS_UPDATE;
    }
    
}

