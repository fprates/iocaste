package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.documents.common.DocumentModelItem;

public class SelectConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolData dataform;
        DataFormToolItem item;
        Context extcontext;
        
        getNavControl().add("show");
        
        extcontext = getExtendedContext();
        dataform = getTool("model");
        dataform.model = extcontext.model;
        for (DocumentModelItem mitem : dataform.model.getItens()) {
            item = dataform.itemInstance(mitem.getName());
            if (item.name.equals("NAME")) {
                item.invisible = false;
                item.focus = item.required = true;
            } else {
                item.invisible = true;
            }
        }
    }
}
