package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.shell.common.Media;
import org.iocaste.shell.common.StyleSheet;

public class StandardPanelConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public StandardPanelConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void execute(PageBuilderContext context) {
        StyleSheet stylesheet;
        Map<String, Map<String, String>> sheet;
        ViewConfig extconfig;
        NavControl navcontrol;
        Media media;
        String submit, mediakey;
        
        stylesheet = context.view.styleSheetInstance();
        for (int i = 0; i < context.appbuildersheet.length; i++) {
            mediakey = (String)context.appbuildersheet[i][0];
            media = stylesheet.instanceMedia(mediakey);
            media.setDevice((String)context.appbuildersheet[i][1]);
            media.setFeature((String)context.appbuildersheet[i][2]);
            sheet = (Map<String, Map<String, String>>)
                    context.appbuildersheet[i][3];
            stylesheet.add(mediakey, sheet);
        }

        context.view.importStyle(stylesheet);
        getElement("outercontent").setStyleClass("content_area");
        getElement("content").addEvent("style", "margin-top:3px");
        
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
        context.view.importStyle(stylesheet);
    }
}
