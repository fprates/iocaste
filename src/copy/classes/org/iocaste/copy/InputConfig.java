package org.iocaste.copy;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;

public class InputConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolData form;
        DataFormToolItem item;
        
        form = getTool("model");
        form.model = "MODEL";
        form.show = new String[] {"NAME", "NAMESPACE"};
        item = form.instance("NAME");
        item.required = item.focus = true;
        
        form = getTool("port");
        form.model = "XTRNL_PORT_HEAD";
        form.show = new String[] {"PORT_NAME"};
        form.instance("PORT_NAME").required = true;
    }

}
