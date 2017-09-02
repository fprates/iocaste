package org.iocaste.runtime.common.page;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.navcontrol.NavControl;
import org.iocaste.runtime.common.style.ViewConfigStyle;

public class StandardPageConfig extends AbstractViewConfig<Context> {
    
    /**
     * 
     * @param context
     * @param page
     * @param name
     */
    private final void config(
            Context context, AbstractPage page, String name) {
        ViewConfig extconfig;
        ViewConfigStyle style;
        AbstractPage child;
        
        extconfig = (child = page.getChild(name)).getConfig();
        if (page.isSubPage(name) || (extconfig == null))
            return;
        style = child.getConfigStyle();
        if (style != null) {
            style.set(page.getStyleSheet());
            style.execute();
        }
        extconfig.run(context);
    }
    
    @Override
    protected void execute(Context context) {
        NavControl navcontrol;
        String submit;
        AbstractPage page;

        getTool("outercontent").style = "content_area";
        getTool("content").attributes.put("style", "margin-top:3px");
        
        page = context.getPage();
        submit = page.getSubmit();
        navcontrol = page.getNavControl();
        if (submit != null)
            navcontrol.submit(submit);
        for (String action : page.getActions())
            navcontrol.add(action);
        for (String key : page.getChildren())
            config(context, page, key);
    }
}
