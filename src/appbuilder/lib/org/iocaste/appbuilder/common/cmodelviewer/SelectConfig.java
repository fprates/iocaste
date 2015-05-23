package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.panel.StandardPanelConfig;
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
    protected void execute(PageBuilderContext context) {
        Map<String, String> style;
        String bodybg;
        InputComponent input;
        NavControl navcontrol;
        DocumentModel model = getManager(cmodel).getModel().getHeader();
        DataForm head = getElement("head");

        bodybg = "#ffffff";
        
        style = context.view.styleSheetInstance().get(".form_content");
        style.put("padding", "2px");
        style.put("background-color", bodybg);
        style.put("width", "100%");
        style.put("top", StandardPanelConfig.CONTENT_TOP);
        style.put("left", StandardPanelConfig.CONTEXT_WIDTH);
        style.put("position", "fixed");
        
        navcontrol = getNavControl();
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