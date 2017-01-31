package org.iocaste.examples.tabsuse;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class TabsUseSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        tabbedpane(parent, "tabs");
        
        tabbedpaneitem("tabs", "header_tab");
        dataform("header_tab", "header");
        
        tabbedpaneitem("tabs", "items_tab");
        tabletool("items_tab", "items");
    }

}
