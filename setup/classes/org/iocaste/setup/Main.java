package org.iocaste.setup;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    public final PageContext init(View view) {
        context = new Context();
        
        return context;
    }
    
    public final InstallData install(Message message) {
        return Install.init(context.function);
    }
}

class Context extends PageContext {
    
}