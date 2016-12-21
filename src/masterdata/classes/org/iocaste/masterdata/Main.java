package org.iocaste.masterdata;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.messages.GetMessages;

public class Main extends AbstractPage {
    private GetMessages messagesget;
    
    public Main() {
        export("install", "install");
        export("messages_get", messagesget = new GetMessages());
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
    public final AbstractContext init(View view) {
        Context context = new Context();
        Messages messages = new Messages();
        
        messages.setContext(context);
        messages.entries();
        messagesget.set(messages);
        return context;
    }
}

class Context extends AbstractContext { }