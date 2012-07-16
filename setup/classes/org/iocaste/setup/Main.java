package org.iocaste.setup;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;

public class Main extends AbstractPage {
    
    public Main() {
        export("install", "install");
    }
    
    public final InstallData install(Message message) {
        return Install.init(this);
    }
}
