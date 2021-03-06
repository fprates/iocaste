package org.iocaste.setup;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    
    public Main() {
        export("install", "install");
    }
    
    @SuppressWarnings("unchecked")
    public final <T extends AbstractContext> T configOnly() {
        return (T)new Context();
    }
    
    @Override
    public final AbstractContext init(View view) {
        return configOnly();
    }
    
    public final InstallData install(Message message) {
        return Install.init(this);
    }
}

class Context extends AbstractContext {
    
}