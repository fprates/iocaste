package org.iocaste.kernel.files;

import java.io.File;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class MakeDirectory extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String[] args = message.get("args");
        
        run(args);
        return null;
    }
    
    public final void run(String... args) {
        String path = FileServices.getPath(args);
        runabsolute(path);
    }
    
    public final void runabsolute(String path) {
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
    }
    
}
