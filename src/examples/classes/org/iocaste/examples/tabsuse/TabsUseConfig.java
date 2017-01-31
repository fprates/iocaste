package org.iocaste.examples.tabsuse;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class TabsUseConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolData dataform;
        TableToolData tabletool;
        
        getNavControl().setTitle(context.view.getPageName());
        
        dataform = getTool("header");
        dataform.model = "EXAMPLES_DFUSE_INPUT";
        
        tabletool = getTool("items");
        tabletool.model = "EXAMPLES_TTUSE_ITEM";
        tabletool.vlines = 10;
        tabletool.mark = false;
        tabletool.mode = TableTool.CONTINUOUS_UPDATE;
    }

}
