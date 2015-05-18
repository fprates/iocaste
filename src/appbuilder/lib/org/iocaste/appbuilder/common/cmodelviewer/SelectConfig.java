package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.panel.StandardPanelConfig;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.StyleSheet;

public class SelectConfig extends AbstractViewConfig {
    private String action, cmodel;
    
    public SelectConfig(String action, String cmodel) {
        this.action = action;
        this.cmodel = cmodel;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        StyleSheet stylesheet;
        String style, bodybg;
        InputComponent input;
        NavControl navcontrol;
        DocumentModel model = getManager(cmodel).getModel().getHeader();
        DataForm head = getElement("head");

        style = ".form_content";
        bodybg = "#ffffff";
        
        stylesheet = context.view.styleSheetInstance();
        stylesheet.put(style, "padding", "2px");
        stylesheet.put(style, "background-color", bodybg);
        stylesheet.put(style, "width", "100%");
        stylesheet.put(style, "top", StandardPanelConfig.CONTENT_TOP);
        stylesheet.put(style, "left", StandardPanelConfig.CONTEXT_WIDTH);
        stylesheet.put(style, "position", "fixed");
        
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