package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.shell.common.StyleSheet;

public class StandardPanelConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public StandardPanelConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        StyleSheet stylesheet;
        Map<String, String> corestyle, appbuilderstyle;
        ViewConfig extconfig;
        NavControl navcontrol;
        String submit;
        
        stylesheet = context.view.styleSheetInstance();
        for (String selector : context.appbuildersheet.keySet()) {
            corestyle = stylesheet.get(selector);
            if (corestyle == null)
                corestyle = stylesheet.newElement(selector);
            appbuilderstyle = context.appbuildersheet.get(selector);
            corestyle.putAll(appbuilderstyle);
        }
        
        getElement("outercontent").setStyleClass("content_area");
        
        navcontrol = getNavControl();
        submit = page.getSubmit();
        if (submit != null)
            navcontrol.submit(submit);
        for (String action : page.getActions())
            navcontrol.add(action);
        
        extconfig = page.getConfig();
        if (extconfig == null)
            return;
        
        config(extconfig);
    }
}
