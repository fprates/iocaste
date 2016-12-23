package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class Main extends AbstractPageBuilder {
    
    @Override
    public void config(PageBuilderContext context) throws Exception {
        Context extcontext = new Context(context);
        context.add("authentic", new MainPage(), extcontext);
        context.add("changesecret", new ChangeSecretPage(), extcontext);
        context.messages = new Messages();
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        // TODO Stub de método gerado automaticamente
        
    }
}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
        set(new Style());
        put("connect", new Connect());
    }
}

class ChangeSecretPage extends AbstractPanelPage {
    
    @Override
    public void execute() {
        set(new ChangeSecretSpec());
        set(new ChangeSecretConfig());
        set(new Style());
        put("changesecret", new ChangeSecret());
    }
}
