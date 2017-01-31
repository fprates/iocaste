package org.iocaste.examples.tabletooluse;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class TableToolUseSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        nodelist(parent, "viewport");
        
        nodelistitem("viewport", "items_node");
        tabletool("items_node", "items");
        
        nodelistitem("viewport", "output_node");
        printarea("output_node");
    }
    
}

