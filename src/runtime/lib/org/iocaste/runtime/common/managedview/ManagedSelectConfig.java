package org.iocaste.runtime.common.managedview;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.page.AbstractViewConfig;

public class ManagedSelectConfig extends AbstractViewConfig<Context> {
    
    @Override
    protected void execute(Context context) {
        DocumentModelItem ns;
        ToolData item;
        ToolData head = getTool("head");
        ManagedViewContext mviewctx = context.mviewctx();
        
//        NavControl navcontrol = getNavControl();
//        
//        navcontrol.setTitle(context.view.getPageName());
//        navcontrol.submit("validate");
        
        head.custommodel = context.runtime().
                getComplexModel(mviewctx.cmodel).getHeader();
        ns = head.custommodel.getNamespace();
        if (mviewctx.nshidden && (ns != null))
            head.nsItemInstance().invisible = true;
        for (DocumentModelItem mitem : head.custommodel.getItens()) {
            item = head.instance(mitem.getName());
            if (!head.custommodel.isKey(mitem))
                item.invisible = true;
            else
                item.focus = item.required = true;
        }
    }
}