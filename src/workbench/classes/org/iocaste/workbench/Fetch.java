package org.iocaste.workbench;

import java.nio.file.Paths;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class Fetch extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String file = message.getString("file");
        Services services = getFunction();
        Iocaste iocaste = new Iocaste(services);
        String path = Paths.get("workbench", file).toString();
        
        iocaste.rmdir("workbench", file);
        iocaste.mkdir("workbench", file);
        iocaste.unzip(path, "transport", file);
        
        return null;
    }

}
