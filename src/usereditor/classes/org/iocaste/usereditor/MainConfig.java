package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolData form;
        DataFormToolItem item;
        
        getNavControl().setTitle("user-selection");
        
        form = getTool("selection");
        form.modelname = "LOGIN";
        form.show = new String[] {"USERNAME"};
        item = form.itemInstance("USERNAME");
        item.focus = true;
        item.sh = "SH_USER";
        item.required = true;
    }
}
