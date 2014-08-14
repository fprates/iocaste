package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class SelectConfig extends AbstractViewConfig {
    private String action, cmodel;
    
    public SelectConfig(String action, String cmodel) {
        this.action = action;
        this.cmodel = cmodel;
    }
    
    @Override
    protected final void execute(PageBuilderContext context) {
        InputComponent input;
        DocumentModel model = getManager(cmodel).getModel().getHeader();
        DataForm head = getElement("head");
        NavControl navcontrol = getNavControl();
        
        navcontrol.add(action);
        head.importModel(model);
        head.setKeyRequired(true);
        for (DocumentModelItem item : model.getItens()) {
            input = head.get(item.getName());
            if (!model.isKey(item)) {
                input.setVisible(false);
                continue;
            }
            
            context.view.setFocus(input);
        }
    }
}