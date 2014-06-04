package org.iocaste.appbuilder.common.editor;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class SelectConfig extends AbstractViewConfig {
    private Manager manager;
    
    public SelectConfig(Manager manager) {
        this.manager = manager;
    }
    
    @Override
    protected final void execute(PageBuilderContext context) {
        InputComponent input;
        DocumentModel model = manager.getModel().getHeader();
        DataForm head = getElement("head");
        NavControl navcontrol = getNavControl();
        
        navcontrol.add("create");
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