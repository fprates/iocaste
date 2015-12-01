package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;

public class SelectionConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolData dataform;
        DataFormToolItem item;
        
        getNavControl().setTitle("dataeditor-selection");
        
        dataform = getDataFormTool("model");
        dataform.modelname = "MODEL";
        dataform.show = new String[] {"NAME"};
        item = dataform.itemInstance("NAME");
        item.required = item.focus = true;

    }

}
