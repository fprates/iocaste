package org.iocaste.infosys;

import javax.servlet.annotation.WebServlet;

import org.iocaste.infosys.install.ConnectionsInstall;
import org.iocaste.infosys.main.MainPage;
import org.iocaste.runtime.common.application.AbstractApplication;
import org.iocaste.runtime.common.install.ApplicationInstall;
import org.iocaste.runtime.common.install.InstallContext;

@WebServlet(name="iocaste-infosys", urlPatterns={"/index.html",
                                                 "/view.html"})
public class Main extends AbstractApplication<InfosysContext> {
    private static final long serialVersionUID = 1857519291675116845L;

    @Override
    public final InfosysContext execute() {
        InfosysContext context = new InfosysContext();
        context.add("main", new MainPage());
        context.set(new Messages());
        return context;
    }
    
    @Override
    public void install(InstallContext installctx) {
        ApplicationInstall appinstall = installctx.get("default");
        appinstall.setLink("INFOSYS", "iocaste-infosys");
        appinstall.addToTaskGroup("ADMIN", "INFOSYS");
        appinstall.setProfile("ADMIN");
        
        installctx.put("connections", new ConnectionsInstall());
    }
}
