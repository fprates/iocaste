package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.style.ViewConfigStyle;

public class StandardPanelConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ViewConfig extconfig;
        NavControl navcontrol;
        String submit;
        ViewConfigStyle style;
        AbstractPanelPage design, page;
        ViewContext viewctx;

        getElement("outercontent").setStyleClass("content_area");
        getElement("content").addAttribute("style", "margin-top:3px");
        
        viewctx = context.getView();
        page = viewctx.getPanelPage();
        navcontrol = getNavControl();
        submit = page.getSubmit();
        if (submit != null)
            navcontrol.submit(submit);
        for (String action : page.getActions())
            navcontrol.add(action);

        design = viewctx.getDesign();
        if (design != null)
            config(design.getConfig());
        
        extconfig = page.getConfig();
        if (extconfig == null)
            return;
        
        style = page.getConfigStyle();
        if (style != null) {
            style.setContext(context);
            style.execute();
            style.getStyleSheet().export(context.view);
        }

        context.storeStyle();
        config(extconfig);
    }
}
