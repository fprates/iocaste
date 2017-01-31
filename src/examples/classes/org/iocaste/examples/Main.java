package org.iocaste.examples;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.portal.AbstractPortalBuilder;
import org.iocaste.appbuilder.common.portal.PortalContext;
import org.iocaste.examples.dataformuse.DataFormUseInstall;
import org.iocaste.examples.dataformuse.DataFormUsePage;
import org.iocaste.examples.helloworld.HelloWorldPage;
import org.iocaste.examples.main.MainInstall;
import org.iocaste.examples.main.MainPage;
import org.iocaste.examples.tabletooluse.TableToolUseInstall;
import org.iocaste.examples.tabletooluse.TableToolUsePage;

public class Main extends AbstractPortalBuilder {

    @Override
    protected PortalContext contextInstance(PageBuilderContext context) {
        return new Context(context);
    }
    
    @Override
    protected void config(PortalContext portalctx) throws Exception {
        PageBuilderContext context = portalctx.getContext();
        
        portalctx.nologin = true;
        context.messages = new Messages();
        instance("main", new MainPage());
        instance("hello-world", new HelloWorldPage());
        instance("dataform-use", new DataFormUsePage());
        instance("tabletool-use", new TableToolUsePage());
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall installctx) {
        installctx.setLink("EXAMPLES", "iocaste-examples");
        installctx.addToTaskGroup("EXAMPLES", "EXAMPLES");
        installctx.setProfile("EXAMPLES");
        installctx.setProgramAuthorization("EXAMPLES.EXECUTE");
        installObject("main", new MainInstall());
        installObject("dataformuse", new DataFormUseInstall());
        installObject("tabletooluse", new TableToolUseInstall());
    }

}
