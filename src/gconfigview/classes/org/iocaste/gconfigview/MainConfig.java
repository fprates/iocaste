package org.iocaste.gconfigview;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.documents.common.DocumentModelItem;

public class MainConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolData form;
        DataFormToolItem input;
        Context extcontext;
        
        extcontext = getExtendedContext();
        
        form = getTool("package");
        form.custommodel = extcontext.globalcfgmodel;
        for (DocumentModelItem item : extcontext.globalcfgmodel.getItens())
            form.instance(item.getName()).invisible = true;
        
        input = form.instance("NAME");
        input.required = true;
        input.invisible = false;
        input.focus = true;
        
        getNavControl().setTitle(Context.TITLES[Context.SELECT]);
    }

}
