package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.StyleSheet;

public 

class StandardPanelConfig extends AbstractViewConfig {
    private static final String SERVICE = "/iocaste-appbuilder/services.html";
    private AbstractPanelPage page;
    
    public StandardPanelConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        ViewConfig extconfig;
        StyleSheet stylesheet;
        Map<String, Map<String, String>> appbuildersheet;
        Map<String, String> corestyle, appbuilderstyle;
        GenericService service;
        Message message;
        NavControl navcontrol;
        String submit;
        
        message = new Message("stylesheet_get");
        service = new GenericService(context.function, SERVICE);
        appbuildersheet = service.invoke(message);
        
        stylesheet = context.view.styleSheetInstance();
        for (String selector : appbuildersheet.keySet()) {
            corestyle = stylesheet.get(selector);
            if (corestyle == null)
                corestyle = stylesheet.newElement(selector);
            appbuilderstyle = appbuildersheet.get(selector);
            corestyle.putAll(appbuilderstyle);
        }
        
        getElement("outercontent").setStyleClass("content_area");
        
        navcontrol = getNavControl();
        for (String action : page.getActions())
            navcontrol.add(action);
        submit = page.getSubmit();
        if (submit != null)
            navcontrol.submit(submit);
        
        extconfig = page.getConfig();
        if (extconfig == null)
            return;
        
        config(extconfig);
    }
}
