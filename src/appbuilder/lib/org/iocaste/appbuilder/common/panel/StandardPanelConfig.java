package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.navcontrol.NavControlDesign;
import org.iocaste.appbuilder.common.style.ViewConfigStyle;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;

public class StandardPanelConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public StandardPanelConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        ViewConfig extconfig;
        NavControl navcontrol;
        String submit;
        ViewConfigStyle style;
        NavControlDesign design;
        
        design = context.getView().getDesign();
        if (design == null)
            setNavControlConfig(context);

        getElement("outercontent").setStyleClass("content_area");
        getElement("content").addAttribute("style", "margin-top:3px");
        
        navcontrol = getNavControl();
        submit = page.getSubmit();
        if (submit != null)
            navcontrol.submit(submit);
        for (String action : page.getActions())
            navcontrol.add(action);
        
        extconfig = page.getConfig();
        if (extconfig == null)
            return;
        
        style = page.getConfigStyle();
        if (style != null) {
            style.setContext(context);
            style.execute();
            style.getStyleSheet().export(context.view);
            context.storeStyle();
        }
        
        config(extconfig);
    }
    
    private final void setNavControlConfig(PageBuilderContext context) {
        String name, style, action;
        Element element;
        ControlComponent control;
        boolean cancellable;
        
        for (int i = 0; i < context.ncconfig.length; i++) {
            name = (String)context.ncconfig[i][0];
            style = (String)context.ncconfig[i][1];
            action = (String)context.ncconfig[i][2];
            cancellable = (boolean)context.ncconfig[i][3];
            element = getElement(name);
            if (element == null)
                continue;
            if (style != null)
                element.setStyleClass(style);
            if (action == null)
                continue;
            control = (ControlComponent)element;
            control.setAction(action);
            control.setCancellable(cancellable);
        }
    }
}
