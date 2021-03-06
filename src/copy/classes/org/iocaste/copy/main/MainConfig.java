package org.iocaste.copy.main;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolData form;
        DataFormToolItem item;
        
        form = getTool("model");
        form.model = "MODEL";
        show(form, "NAME", "NAMESPACE");
        item = form.instance("NAME");
        item.required = item.focus = true;
        
        form = getTool("port");
        form.model = "XTRNL_PORT_HEAD";
        show(form, "PORT_NAME");
        
        form = getTool("db");
        form.model = "COPY_EXTERNAL_DB";
    }

}
