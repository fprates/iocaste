package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.documents.common.DocumentModelItem;

public class SelectConfig extends AbstractViewConfig {
    private String cmodel;
    
    public SelectConfig(String cmodel) {
        this.cmodel = cmodel;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolItem item;
        DataFormToolData head = getDataFormTool("head");
        NavControl navcontrol = getNavControl();
        
        navcontrol.setTitle(context.view.getPageName());
        navcontrol.submit("validate");
        
        head.model = getManager(cmodel).getModel().getHeader();
        for (DocumentModelItem mitem : head.model.getItens()) {
            item = head.itemInstance(mitem.getName());
            if (!head.model.isKey(mitem))
                item.invisible = true;
            else
                item.focus = item.required = true;
        }
    }
}