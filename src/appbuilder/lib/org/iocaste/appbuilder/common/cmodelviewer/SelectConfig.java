package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;

public class SelectConfig extends AbstractViewConfig {
    private String cmodel;
    
    public SelectConfig(String cmodel) {
        this.cmodel = cmodel;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolItem item;
        DataFormToolData head = getTool("head");
        NavControl navcontrol = getNavControl();
        
        navcontrol.setTitle(context.view.getPageName());
        navcontrol.submit("validate");
        
        head.custommodel = new Documents(context.function).
                getComplexModel(cmodel).getHeader();
        for (DocumentModelItem mitem : head.custommodel.getItens()) {
            item = head.instance(mitem.getName());
            if (!head.custommodel.isKey(mitem))
                item.invisible = true;
            else
                item.focus = item.required = true;
        }
    }
}