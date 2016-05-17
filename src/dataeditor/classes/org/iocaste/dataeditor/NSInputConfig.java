package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;

public class NSInputConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        DataFormToolData form;
        DataFormToolItem item;
        
        extcontext = getExtendedContext();
        form = getTool("ns");
        item = form.itemInstance("NSKEY");
        item.element = extcontext.nsitem.getDataElement();
        item.required = item.focus = true;
        
        getNavControl().setTitle("ns.key.input");
    }

}
