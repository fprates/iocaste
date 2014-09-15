package org.template;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;

/**
 * Template par módulo interno do iocaste.
 * @author francisco.prates
 *
 */
public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        ViewContext main = context.instance("main");
        
        main.set(new MainSpec());
        main.set(new MainConfig());
        main.put("bind", new Bind());
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("BIND", "iocaste-template");
        defaultinstall.addToTaskGroup("TEMPLATE", "BIND");
        defaultinstall.setProfile("TEMPLATE");
        defaultinstall.setProgramAuthorization("TEMPLATE.EXECUTE");
        installObject("messages", new Install());
    }
}
