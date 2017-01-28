package org.iocaste.masterdata;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.messages.GetMessages;

public class Main extends AbstractPage {
    private GetMessages messagesget;
    private Messages messages;
    
    public Main() {
        export("install", "install");
        export("messages_get", messagesget = new GetMessages());
        messagesget.set(messages = new Messages());
    }
    
    @SuppressWarnings("unchecked")
    public final <T extends AbstractContext> T configOnly() {
        Context context = new Context();
        context.messages = messages;
        messages.entries();
        return (T)context;
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
        return configOnly();
    }
}

class Context extends AbstractContext { }