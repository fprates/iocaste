package org.iocaste.dataview;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolData form;
        DataFormToolItem item;
        Context extcontext = getExtendedContext();
        
        form = getTool("model");
        form.custommodel = extcontext.modelmodel;
        show(form, "NAME");
        
        item = form.instance("NAME");
        item.required = item.focus = true;
        
        getNavControl().setTitle("dataview-selection");
    }

}
