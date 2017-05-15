package org.iocaste.infosys;

import javax.servlet.annotation.WebServlet;

import org.iocaste.runtime.common.application.AbstractApplication;
import org.iocaste.runtime.common.install.ApplicationInstall;
import org.iocaste.runtime.common.install.InstallContext;
import org.iocaste.runtime.common.page.AbstractPage;

@WebServlet(name="iocaste-infosys", urlPatterns={"/index.html",
                                                 "/view.html"})
public class Main extends AbstractApplication<InfosysContext> {
    private static final long serialVersionUID = 1857519291675116845L;

    @Override
    public final InfosysContext execute() {
        InfosysContext context = new InfosysContext();
        context.add("main", new MainPage());
        return context;
    }
    
    @Override
    public void install(InstallContext installctx) {
        ApplicationInstall appinstall = installctx.get("default");
        appinstall.setLink("INFOSYS", "iocaste-infosys");
        appinstall.addToTaskGroup("ADMIN", "INFOSYS");
        appinstall.setProfile("ADMIN");
    }
}

class MainPage extends AbstractPage {

    @Override
    public void execute() throws Exception {
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
    }
    
}
