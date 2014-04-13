package org.iocaste.masterdata;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    public InstallData install(Message message) {
        InstallContext icontext = new InstallContext();
        
        Currency.init(icontext);
        Country.init(icontext);
        Region.init(icontext);
        Units.init(icontext);
        
        return icontext.data;
    }
    
    @Override
    public final PageContext init(View view) {
        return new Context();
    }
}

class Context extends PageContext { }