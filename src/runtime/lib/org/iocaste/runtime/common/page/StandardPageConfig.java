package org.iocaste.runtime.common.page;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.navcontrol.NavControl;
import org.iocaste.runtime.common.style.ViewConfigStyle;

public class StandardPageConfig extends AbstractViewConfig<Context> {
    
    @Override
    protected void execute(Context context) {
        ViewConfig extconfig;
        NavControl navcontrol;
        String submit;
        ViewConfigStyle style;
        AbstractPage page, child;

        getTool("outercontent").style = "content_area";
        getTool("content").attributes.put("style", "margin-top:3px");
        
        page = context.getPage();
        submit = page.getSubmit();
        navcontrol = page.getNavControl();
        if (submit != null)
            navcontrol.submit(submit);
        for (String action : page.getActions())
            navcontrol.add(action);
        
        for (String key : page.getChildren()) {
            extconfig = (child = page.getChild(key)).getConfig();
            if (page.isSubPage(key) || (extconfig == null))
            	continue;
            style = child.getConfigStyle();
            if (style != null) {
            	style.set(page.getStyleSheet());
                style.execute();
            }
            config(extconfig);
        }
    }
}
