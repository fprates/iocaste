package org.quantic.template;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

/**
 * Template par módulo interno do iocaste.
 * @author francisco.prates
 *
 */
public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        context.messages = new Messages();
        context.add("main", new MainPage(), new Context(context));
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        // autorização de execução
        defaultinstall.setProgramAuthorization("TESTE");
        defaultinstall.setProfile("EXAMPLES");
        
        // link
        defaultinstall.setLink("TESTE", "externaltest");
        defaultinstall.addToTaskGroup("TESTE", "TESTE");
    }
}

class Context extends AbstractExtendedContext {

    public Context(PageBuilderContext context) {
        super(context);
    }
    
}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        action("bind", new Bind());
    }
    
}