package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.appbuilder.common.style.CommonStyle;

public class Main extends AbstractPageBuilder {
    
    @Override
    public void config(PageBuilderContext context) throws Exception {
        StandardPanel panel;
        Context extcontext;
        CommonStyle profile;

        extcontext = new Context();
        
        panel = new StandardPanel(context);
        panel.instance("authentic", new MainPage(), extcontext);
        panel.instance("changesecret", new ChangeSecretPage(), extcontext);
        
        profile = CommonStyle.get();
        profile.head.bgcolor = "#3030ff";
        profile.content.bgcolor = "#202020";
        profile.content.width = "100%";
        
        add(extcontext.messages);
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
        
        put("connect", new Connect());
    }
}

class ChangeSecretPage extends AbstractPanelPage {
    
    @Override
    public void execute() {
        set(new ChangeSecretSpec());
        set(new ChangeSecretConfig());
        put("changesecret", new ChangeSecret());
    }
}
