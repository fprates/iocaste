package org.iocaste.examples.dataformuse;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class DataFormUsePageSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        nodelist(parent, "view_port");
        
        nodelistitem("view_port", "input_node");
        dataform("input_node", "input");
        
        nodelistitem("view_port", "output_node");
        text("output_node", "dfuse_output");
    }
    
}

