package org.iocaste.infosys.main;

import org.iocaste.runtime.common.page.AbstractPage;

public class MainPage extends AbstractPage {

    @Override
    public void execute() throws Exception {
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
        put("connections_info", new GetConnectionInfo());
        
//        run("connections_info");
    }
    
}
