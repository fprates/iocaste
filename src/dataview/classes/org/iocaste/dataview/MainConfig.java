package org.iocaste.dataview;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        DataFormToolData form;
        DataFormToolItem item;
        
        extcontext = getExtendedContext();
        
        form = getTool("model");
        form.custommodel = extcontext.modelmodel;
        form.show = new String[] {"NAME"};
        item = form.itemInstance("NAME");
        item.required = item.focus = true;
        
        getNavControl().setTitle("dataview-selection");
    }

}
