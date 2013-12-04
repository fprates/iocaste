package org.iocaste.packagetool;

import java.util.Set;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

public class InstallTexts {

    public static final void init(Set<String> texts, State state) {
        GenericService service = new GenericService(state.function,
                InstallData.TXTEDITOR_SERVERNAME);
        Message message = new Message();
        
        message.setId("register");
        for (String name : texts) {
            message.add("name", name);
            service.invoke(message);
            message.clear();
            Registry.add(name, "PACKAGE_TEXT", state);
        }
    }
    
    public static final void uninstall(String name, Function function) {
        GenericService service = new GenericService(function,
                InstallData.TXTEDITOR_SERVERNAME);
        Message message = new Message();
        
        message.setId("unregister");
        message.add("name", name);
        service.invoke(message);
    }
}
